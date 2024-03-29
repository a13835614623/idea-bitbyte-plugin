package com.zzk.idea.bitbyte.action.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.intellij.codeInsight.CodeInsightBundle;
import com.intellij.codeInsight.CodeInsightUtil;
import com.intellij.codeInsight.FileModificationService;
import com.intellij.codeInsight.daemon.impl.analysis.ImportsHighlightUtil;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.java.JavaBundle;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.JavaProjectRootsUtil;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.SourceFolder;
import com.intellij.openapi.roots.TestModuleProperties;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiImportList;
import com.intellij.psi.PsiImportStatementBase;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.PsiReferenceList;
import com.intellij.psi.codeStyle.JavaCodeStyleSettings;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.GlobalSearchScopesCore;
import com.intellij.psi.util.PsiUtilCore;
import com.intellij.refactoring.PackageWrapper;
import com.intellij.refactoring.move.moveClassesOrPackages.MoveClassesOrPackagesUtil;
import com.intellij.refactoring.util.RefactoringUtil;
import com.intellij.refactoring.util.classMembers.MemberInfo;
import com.intellij.testIntegration.TestFramework;
import com.intellij.testIntegration.TestIntegrationUtils;
import com.intellij.testIntegration.createTest.JavaTestGenerator;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.containers.ContainerUtil;
import com.zzk.idea.bitbyte.constants.Message;
import com.zzk.idea.bitbyte.constants.NamingMethod;
import com.zzk.idea.bitbyte.constants.TestActionType;
import com.zzk.idea.bitbyte.settings.AppSettingsState;
import com.zzk.idea.bitbyte.settings.CreateTestMethodConfigItem;
import com.zzk.idea.bitbyte.settings.CreateTestMethodState;
import com.zzk.idea.bitbyte.util.PsiUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.model.java.JavaSourceRootType;

/**
 * CreateTestMethodAction
 *
 * @author 张子宽
 * @date 2023/07/25
 */
@SuppressWarnings("ALL")
public class BaseCreateTestAction extends AnAction {

    /**
     * 类
     */
    private final PsiClass psiClass;
    /**
     * 方法
     */
    private final List<PsiMethod> methods;
    /**
     * 测试框架
     */
    private final TestFramework testFramework;
    /**
     * 类名后缀
     */
    private final TestActionType testActionType;

    public BaseCreateTestAction(PsiMethod psiMethod, TestFramework testFramework, TestActionType testActionType) {
        this.methods = List.of(psiMethod);
        this.psiClass = psiMethod.getContainingClass();
        this.testFramework = testFramework;
        this.testActionType = testActionType;
        getTemplatePresentation().setText(testActionType.getCreateText());
    }

    public BaseCreateTestAction(PsiClass psiClass, List<PsiMethod> methods, TestFramework testFramework, TestActionType testActionType) {
        this.psiClass = psiClass;
        this.methods = methods;
        this.testFramework = testFramework;
        this.testActionType = testActionType;
        getTemplatePresentation().setText(testActionType.getCreateText());
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        VirtualFile virtualFile = psiClass.getContainingFile().getVirtualFile();
        PsiDirectory srcDir = psiClass.getContainingFile().getContainingDirectory();
        Module srcModule = ModuleUtilCore.findModuleForFile(virtualFile, project);
        if (srcModule == null) {
            Message.NOT_FOUND_SRC_MODULE.showErrorDialog();
            return;
        }
        PsiPackage srcPackage = JavaDirectoryService.getInstance().getPackage(srcDir);
        if (srcPackage == null) {
            Message.NOT_FOUND_SRC_PACKAGE.showErrorDialog();
            return;
        }
        Module testModule = suggestModuleForTests(project, srcModule);
        PsiClass srcClass = PsiUtil.firstPsiJavaClass(project, virtualFile).orElseThrow();
        if (testFramework == null) {
            Message.NOT_FOUND_TEST_FRAMEWORK.showErrorDialog();
            return;
        }
        List<VirtualFile> testRootUrls = computeTestRoots(testModule);
        if (testRootUrls.isEmpty() && computeSuitableTestRootUrls(testModule).isEmpty()) {
            testModule = srcModule;
            if (Messages.showOkCancelDialog(project, JavaBundle.message("dialog.message.create.test.in.the.same.source.root"),
                    JavaBundle.message("dialog.title.no.test.roots.found"), Messages.getQuestionIcon()) !=
                    Messages.OK) {
                return;
            }
        }
        PsiDirectory psiDirectory = selectTargetDirectory(project, testModule, srcPackage.getQualifiedName());
        PsiClass testClass = createTestClass(psiDirectory, srcClass, testFramework, suggestTestClassName(srcClass));
        if (testClass == null) {
            Message.TEST_CLASS_CREATE_FAIL.showErrorDialog();
            return;
        }
        // todo 后续支持设置自定义父类
//        if (!Comparing.strEqual(superClassName, defaultSuperClass)) {
//            addSuperClass(testClass, project, superClassName);
//        }
        PsiFile file = testClass.getContainingFile();
        WriteCommandAction.runWriteCommandAction(project, () -> {
            Set<String> testClassExistMethodNames = Arrays.stream(testClass.getMethods()).map(PsiMethod::getName)
                    .collect(Collectors.toSet());
            JavaTestGenerator.addTestMethods(CodeInsightUtil.positionCursorAtLBrace(project, file, testClass),
                    testClass,
                    srcClass,
                    testFramework,
                    methods.stream().map(MemberInfo::new).collect(Collectors.toList()),
                    false,
                    false);
            // 处理测试方法名称
            handleTestMethodName(testClass, testClassExistMethodNames);
            writeTestCode(file);
        });
    }

    private void handleTestMethodName(PsiClass testClass, Set<String> testClassExistMethodNames) {
        CreateTestMethodState createTestMethodState = AppSettingsState.getInstance().getCreateTestMethodState();
        NamingMethod testMethodNamingMethod = createTestMethodState.getTestMethodNamingMethod();
        String testMethodNamePreFix = createTestMethodState
                .getTestMethodNamePreFix();
        Set<String> srcMethodNames = methods.stream().map(PsiMethod::getName).collect(Collectors.toSet());
        Arrays.stream(testClass.getMethods())
                .filter(x -> {
                    return !testClassExistMethodNames.contains(x.getName());
                })
                .forEach(x -> {
                    String newName = testMethodNamingMethod.getFromCamelCaseFunction()
                            .apply(x.getName());
                    if (testMethodNamingMethod == NamingMethod.CAMEL_CASE) {
                        newName = StringUtils.capitalize(newName);
                    } else if (testMethodNamingMethod == NamingMethod.SNAKE_CASE) {
                        newName = "_" + newName;
                    }
                    x.setName(testMethodNamePreFix + newName);
                });
    }

    private static void writeTestCode(PsiFile file) {
        if (file instanceof PsiJavaFile) {
            PsiImportList list = ((PsiJavaFile) file).getImportList();
            if (list != null) {
                PsiImportStatementBase[] importStatements = list.getAllImportStatements();
                if (importStatements.length > 0) {
                    VirtualFile virtualFile = PsiUtilCore.getVirtualFile(list);
                    Set<String> imports = new HashSet<>();
                    for (PsiImportStatementBase base : importStatements) {
                        imports.add(base.getText());
                    }
                    virtualFile.putCopyableUserData(ImportsHighlightUtil.IMPORTS_FROM_TEMPLATE, imports);
                }
            }
        }
    }

    private static void addSuperClass(PsiClass targetClass, Project project, String superClassName) throws IncorrectOperationException {
        if (superClassName == null) return;
        final PsiReferenceList extendsList = targetClass.getExtendsList();
        if (extendsList == null) return;

        PsiElementFactory ef = JavaPsiFacade.getElementFactory(project);
        PsiJavaCodeReferenceElement superClassRef;

        PsiClass superClass = findClass(project, superClassName);
        if (superClass != null) {
            superClassRef = ef.createClassReferenceElement(superClass);
        } else {
            superClassRef = ef.createFQClassNameReferenceElement(superClassName, GlobalSearchScope.allScope(project));
        }
        final PsiJavaCodeReferenceElement[] referenceElements = extendsList.getReferenceElements();
        if (referenceElements.length == 0) {
            extendsList.add(superClassRef);
        } else {
            referenceElements[0].replace(superClassRef);
        }
    }

    @Nullable
    private static PsiClass findClass(Project project, String fqName) {
        GlobalSearchScope scope = GlobalSearchScope.allScope(project);
        return JavaPsiFacade.getInstance(project).findClass(fqName, scope);
    }


    @Nullable
    private static PsiClass createTestClass(PsiDirectory targetDirectory, PsiClass srcClass, TestFramework testFrameworkDescriptor, String testClassName) {
        final FileTemplateDescriptor fileTemplateDescriptor = TestIntegrationUtils.MethodKind.TEST_CLASS.getFileTemplateDescriptor(testFrameworkDescriptor);

        final PsiPackage aPackage = JavaDirectoryService.getInstance().getPackage(targetDirectory);
        if (aPackage != null) {
            final GlobalSearchScope scope = GlobalSearchScopesCore.directoryScope(targetDirectory, false);
            final PsiClass[] classes = aPackage.findClassByShortName(testClassName, scope);
            if (classes.length > 0) {
                if (!FileModificationService.getInstance().preparePsiElementForWrite(classes[0])) {
                    return null;
                }
                return classes[0];
            }
        }
        if (fileTemplateDescriptor != null) {
            final PsiClass classFromTemplate = createTestClassFromCodeTemplate(srcClass, testClassName, fileTemplateDescriptor, targetDirectory);
            if (classFromTemplate != null) {
                return classFromTemplate;
            }
        }
        return JavaDirectoryService.getInstance().createClass(targetDirectory, testClassName);
    }

    private static PsiClass createTestClassFromCodeTemplate(PsiClass srcClass, String className,
            FileTemplateDescriptor fileTemplateDescriptor,
            PsiDirectory targetDirectory) {
        final String templateName = fileTemplateDescriptor.getFileName();
        final FileTemplate fileTemplate = FileTemplateManager.getInstance(targetDirectory.getProject())
                .getCodeTemplate(templateName);
        final Properties defaultProperties = FileTemplateManager.getInstance(targetDirectory.getProject())
                .getDefaultProperties();
        Properties properties = new Properties(defaultProperties);
        properties.setProperty(FileTemplate.ATTRIBUTE_NAME, className);
        if (srcClass != null && srcClass.isValid()) {
            properties.setProperty(FileTemplate.ATTRIBUTE_CLASS_NAME, className);
        }
        try {
            final PsiElement psiElement = FileTemplateUtil.createFromTemplate(fileTemplate, templateName, properties, targetDirectory);
            if (psiElement instanceof PsiClass) {
                return (PsiClass) psiElement;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }


    public static Stream<SourceFolder> suitableTestSourceFolders(@NotNull Module module) {
        Predicate<SourceFolder> forGeneratedSources = JavaProjectRootsUtil::isForGeneratedSources;
        return Arrays.stream(ModuleRootManager.getInstance(module).getContentEntries())
                .flatMap(entry -> entry.getSourceFolders(JavaSourceRootType.TEST_SOURCE).stream())
                .filter(forGeneratedSources.negate());
    }

    public Module suggestModuleForTests(@NotNull Project project, @NotNull Module productionModule) {
        Module[] projectModules = ModuleManager.getInstance(project).getModules();
        CreateTestMethodState createTestMethodState = AppSettingsState.getInstance().getCreateTestMethodState();
        List<CreateTestMethodConfigItem> items = createTestMethodState.getItems();
        Optional<Module> selectModule = items.stream()
                .filter(x -> isEnableConfigItem(x))
                .filter(x -> Objects.equals(x.getProjectName(), project.getName()))
                .findFirst()
                .map(CreateTestMethodConfigItem::getTestModuleName)
                .flatMap(x -> Arrays.stream(projectModules).filter(pm -> Objects.equals(pm.getName(), x)).findFirst());
        return selectModule
                .orElseGet(() -> defaultSelectModule(productionModule, projectModules));
    }

    private boolean isEnableConfigItem(CreateTestMethodConfigItem x) {
        return x.getEnableUnitTest() && TestActionType.UNIT_TEST == testActionType ||
                x.getEnableIntegrationTest() && TestActionType.INTEGRATION_TEST == testActionType;
    }

    private static Module defaultSelectModule(@NotNull Module productionModule, Module[] projectModules) {
        for (Module module : projectModules) {
            if (productionModule.equals(TestModuleProperties.getInstance(module).getProductionModule())) {
                return module;
            }
        }

        if (computeSuitableTestRootUrls(productionModule).isEmpty()) {
            final HashSet<Module> modules = new HashSet<>();
            ModuleUtilCore.collectModulesDependsOn(productionModule, modules);
            modules.remove(productionModule);
            List<Module> modulesWithTestRoot = modules.stream()
                    .filter(module -> !computeSuitableTestRootUrls(module).isEmpty())
                    .limit(2)
                    .collect(Collectors.toList());
            if (modulesWithTestRoot.size() == 1) {
                return modulesWithTestRoot.get(0);
            }
        }

        return productionModule;
    }

    static List<String> computeSuitableTestRootUrls(@NotNull Module module) {
        return suitableTestSourceFolders(module).map(SourceFolder::getUrl).collect(Collectors.toList());
    }


    @Nullable
    private PsiDirectory selectTargetDirectory(Project myProject, Module myTargetModule, String packageName) throws IncorrectOperationException {
        final PackageWrapper targetPackage = new PackageWrapper(PsiManager.getInstance(myProject), packageName);

        final VirtualFile selectedRoot = ReadAction.compute(() -> {
            final List<VirtualFile> testFolders = computeTestRoots(myTargetModule);
            List<VirtualFile> roots;
            if (testFolders.isEmpty()) {
                roots = new ArrayList<>();
                List<String> urls = computeSuitableTestRootUrls(myTargetModule);
                for (String url : urls) {
                    try {
                        ContainerUtil.addIfNotNull(roots, VfsUtil.createDirectories(VfsUtilCore.urlToPath(url)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (roots.isEmpty()) {
                    JavaProjectRootsUtil.collectSuitableDestinationSourceRoots(myTargetModule, roots);
                }
                if (roots.isEmpty()) return null;
            } else {
                roots = new ArrayList<>(testFolders);
            }

            if (roots.size() == 1) {
                return roots.get(0);
            } else {
                PsiDirectory defaultDir = chooseDefaultDirectory(myProject, myTargetModule, targetPackage.getDirectories(), roots);
                return MoveClassesOrPackagesUtil.chooseSourceRoot(targetPackage, roots, defaultDir);
            }
        });

        if (selectedRoot == null) return null;

        return WriteCommandAction.writeCommandAction(myProject)
                .withName(CodeInsightBundle.message("create.directory.command"))
                .compute(() -> RefactoringUtil.createPackageDirectoryInSourceRoot(targetPackage, selectedRoot));
    }

    protected static List<VirtualFile> computeTestRoots(@NotNull Module mainModule) {
        if (!computeSuitableTestRootUrls(mainModule).isEmpty()) {
            //create test in the same module, if the test source folder doesn't exist yet it will be created
            return suitableTestSourceFolders(mainModule)
                    .map(SourceFolder::getFile)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        //suggest to choose from all dependencies modules
        final HashSet<Module> modules = new HashSet<>();
        ModuleUtilCore.collectModulesDependsOn(mainModule, modules);
        return modules.stream()
                .flatMap(BaseCreateTestAction::suitableTestSourceFolders)
                .map(SourceFolder::getFile)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    @Nullable
    private PsiDirectory chooseDefaultDirectory(Project myProject, Module myTargetModule, PsiDirectory[] directories, List<VirtualFile> roots) {
        List<PsiDirectory> dirs = new ArrayList<>();
        PsiManager psiManager = PsiManager.getInstance(myProject);
        for (VirtualFile file : ModuleRootManager.getInstance(myTargetModule)
                .getSourceRoots(JavaSourceRootType.TEST_SOURCE)) {
            final PsiDirectory dir = psiManager.findDirectory(file);
            if (dir != null) {
                dirs.add(dir);
            }
        }
        if (!dirs.isEmpty()) {
            for (PsiDirectory dir : dirs) {
                final String dirName = dir.getVirtualFile().getPath();
                if (dirName.contains("generated")) continue;
                return dir;
            }
            return dirs.get(0);
        }
        for (PsiDirectory dir : directories) {
            final VirtualFile file = dir.getVirtualFile();
            for (VirtualFile root : roots) {
                if (VfsUtilCore.isAncestor(root, file, false)) {
                    final PsiDirectory rootDir = psiManager.findDirectory(root);
                    if (rootDir != null) {
                        return rootDir;
                    }
                }
            }
        }
        return ModuleManager.getInstance(myProject)
                .getModuleDependentModules(myTargetModule)
                .stream()
                .flatMap(module -> ModuleRootManager.getInstance(module).getSourceRoots(JavaSourceRootType.TEST_SOURCE)
                        .stream())
                .map(psiManager::findDirectory)
                .filter(Objects::nonNull)
                .findFirst().orElse(null);
    }

    public String suggestTestClassName(PsiClass targetClass) {
        JavaCodeStyleSettings customSettings = JavaCodeStyleSettings.getInstance(targetClass.getContainingFile());
        String prefix = customSettings.TEST_NAME_PREFIX;
        return prefix + targetClass.getName() + testActionType.getDefaultSuffix();
    }

}

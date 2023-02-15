//package com.zzk.idea.jsonschema.action.linemarker;
//
//import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
//import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
//import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
//import com.intellij.psi.PsiElement;
//import com.intellij.psi.PsiLiteralExpression;
//import com.intellij.psi.impl.source.tree.java.PsiJavaTokenImpl;
//import com.zzk.idea.jsonschema.constants.Icons;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.Collection;
//import java.util.List;
//
//public class SimpleLineMarkerProvider extends RelatedItemLineMarkerProvider {
//
//  @Override
//  protected void collectNavigationMarkers(@NotNull PsiElement element,
//                                          @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result) {
//    // This must be an element with a literal expression as a parent
//    if (!(element instanceof PsiJavaTokenImpl) || !(element.getParent() instanceof PsiLiteralExpression)) {
//      return;
//    }
//
//    // The literal expression must start with the Simple language literal expression
//    PsiLiteralExpression literalExpression = (PsiLiteralExpression) element.getParent();
//    PsiElement prevSibling = element.getPrevSibling();
//
//    NavigationGutterIconBuilder<PsiElement> builder =
//            NavigationGutterIconBuilder.create(Icons.TEST)
//                    .setTargets(List.of())
//                    .setTooltipText("Navigate to Simple language property");
//    RelatedItemLineMarkerInfo<PsiElement> lineMarkerInfo = builder.createLineMarkerInfo(element);
//    result.add(lineMarkerInfo);
//  }
//
//}
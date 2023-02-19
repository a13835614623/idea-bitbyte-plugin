package com.zzk.idea.jsonschema.action.linemarker;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.zzk.idea.jsonschema.constants.Icons;
import com.zzk.idea.jsonschema.constants.SpringRequestMapping;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class SimpleLineMarkerProvider extends RelatedItemLineMarkerProvider {

	@Override
	protected void collectNavigationMarkers(@NotNull PsiElement element,
			@NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result) {
		// This must be an element with a literal expression as a parent
		if (!(element instanceof PsiMethod)) {
			return;
		}
		// The literal expression must start with the Simple language literal expression
		PsiMethod psiMethod = (PsiMethod) element;


		NavigationGutterIconBuilder<PsiElement> builder =
				NavigationGutterIconBuilder.create(Icons.TEST)
						.setTargets(List.of())
						.setTooltipText("Navigate to Simple language property");
		RelatedItemLineMarkerInfo<PsiElement> lineMarkerInfo = builder.createLineMarkerInfo(element);

		result.add(lineMarkerInfo);
	}

}
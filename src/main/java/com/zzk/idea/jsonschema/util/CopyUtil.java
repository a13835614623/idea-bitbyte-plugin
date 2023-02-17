package com.zzk.idea.jsonschema.util;

import java.awt.datatransfer.StringSelection;

import com.intellij.openapi.ide.CopyPasteManager;
import org.apache.commons.lang3.StringUtils;

public class CopyUtil {

	public static void setClipBoardContent(String clipBoardContent) {
		if (StringUtils.isEmpty(clipBoardContent)) {
			return;
		}
		CopyPasteManager.getInstance().setContents(new StringSelection(clipBoardContent));
	}
}

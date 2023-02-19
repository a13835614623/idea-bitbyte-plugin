package com.zzk.idea.jsonschema.util;

import java.awt.datatransfer.StringSelection;

import com.intellij.internal.statistic.eventLog.EventLogBuild;
import com.intellij.internal.statistic.eventLog.LogEventRecordRequest;
import com.intellij.internal.statistic.eventLog.connection.EventLogSendListener;
import com.intellij.notification.EventLog;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.ui.MessageDialogBuilder;
import com.intellij.openapi.ui.MessageUtil;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.messages.MessageDialog;
import org.apache.commons.lang3.StringUtils;

public class CopyUtil {

	public static void setClipBoardContent(String clipBoardContent) {
		if (StringUtils.isEmpty(clipBoardContent)) {
			return;
		}
		CopyPasteManager.getInstance().setContents(new StringSelection(clipBoardContent));
	}
}

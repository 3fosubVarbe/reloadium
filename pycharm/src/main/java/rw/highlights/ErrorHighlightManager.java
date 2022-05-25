package rw.highlights;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.impl.DocumentMarkupModel;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileWrapper;
import com.intellij.xdebugger.ui.DebuggerColors;
import org.jetbrains.annotations.VisibleForTesting;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.intellij.codeInsight.hint.HintUtil.ERROR_COLOR_KEY;
import static com.intellij.openapi.editor.colors.EditorColorsUtil.getGlobalOrDefaultColor;


public class ErrorHighlightManager {
    Map<File, List<Highlighter>> all;

    @VisibleForTesting
    public static ErrorHighlightManager singleton;

    Project project;

    @VisibleForTesting
    public ErrorHighlightManager(Project project) {
        this.all = new HashMap<>();
        this.project = project;
    }

    public void add(File file, int line) {
        if (!this.all.containsKey(file)) {
            this.all.put(file, new ArrayList<>());
        }

        Highlighter highlighter = new Highlighter(this.project, file, line, getGlobalOrDefaultColor(ERROR_COLOR_KEY),
                0, false);
        this.all.get(file).add(highlighter);

        highlighter.show();
    }

    public void clearFile(File file) {
        List<Highlighter> highlighters = this.all.get(file);
        if (highlighters == null) {
            return;
        }

        for (Highlighter h : highlighters) {
            h.hide();
        }
        highlighters.clear();
    }

    public void clearAll() {
        for (File f : this.all.keySet()) {
            this.clearFile(f);
        }
        this.all.clear();
    }

    public void activate() {
        for (List<Highlighter> hs : this.all.values()) {
            for (Highlighter h : hs) {
                h.show();
            }
        }
    }

    public void deactivate() {
        for (List<Highlighter> hs : this.all.values()) {
            for (Highlighter h : hs) {
                h.hide();
            }
        }
    }
}

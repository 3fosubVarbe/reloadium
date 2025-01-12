package rw.session.events;

import com.google.gson.annotations.SerializedName;
import com.intellij.openapi.diagnostic.Logger;
import rw.dialogs.DialogFactory;

public class ThreadErrorEvent extends FileError {
    public static final String ID = "ThreadError";
    private static final Logger LOGGER = Logger.getInstance(ThreadErrorEvent.class);
    @SerializedName("thread_id")
    private String threadId;

    @SerializedName("frame_id")
    private Long frameId;

    @Override
    public void handle() {
        LOGGER.info("Handling ThreadErrorEvent");
        this.handler.getThreadErrorManager().onThreadError(this);
        DialogFactory.get().showFirstThreadErrorDialog(this.handler.getProject());
    }

    public String getThreadId() {
        return this.threadId;
    }

    public Long getFramenId() {
        return this.frameId;
    }
}

package com.alpay.codenotes.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class StudyNoteItem implements Serializable {
    private String mToDoText;
    private boolean mHasReminder;
    private int mTodoColor;
    private Date mToDoDate;
    private UUID mTodoIdentifier;
    private static final String TODOTEXT = "todotext";
    private static final String TODOREMINDER = "todoreminder";
    private static final String TODOCOLOR = "todocolor";
    private static final String TODODATE = "tododate";
    private static final String TODOIDENTIFIER = "todoidentifier";


    public StudyNoteItem(String todoBody, boolean hasReminder, Date toDoDate){
        mToDoText = todoBody;
        mHasReminder = hasReminder;
        mToDoDate = toDoDate;
        mTodoColor = 1677725;
        mTodoIdentifier = UUID.randomUUID();
    }

    public StudyNoteItem(JSONObject jsonObject) throws JSONException {
        mToDoText = jsonObject.getString(TODOTEXT);
        mHasReminder = jsonObject.getBoolean(TODOREMINDER);
        mTodoColor = jsonObject.getInt(TODOCOLOR);
        mTodoIdentifier = UUID.fromString(jsonObject.getString(TODOIDENTIFIER));

        if(jsonObject.has(TODODATE)){
            mToDoDate = new Date(jsonObject.getLong(TODODATE));
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TODOTEXT, mToDoText);
        jsonObject.put(TODOREMINDER, mHasReminder);
        if(mToDoDate!=null){
            jsonObject.put(TODODATE, mToDoDate.getTime());
        }
        jsonObject.put(TODOCOLOR, mTodoColor);
        jsonObject.put(TODOIDENTIFIER, mTodoIdentifier.toString());

        return jsonObject;
    }


    public StudyNoteItem(){
        this("Clean my room", true, new Date());
    }

    public String getToDoText() {
        return mToDoText;
    }

    public void setToDoText(String mToDoText) {
        this.mToDoText = mToDoText;
    }

    public boolean hasReminder() {
        return mHasReminder;
    }

    public void setHasReminder(boolean mHasReminder) {
        this.mHasReminder = mHasReminder;
    }

    public Date getToDoDate() {
        return mToDoDate;
    }

    public int getTodoColor() {
        return mTodoColor;
    }

    public void setTodoColor(int mTodoColor) {
        this.mTodoColor = mTodoColor;
    }

    public void setToDoDate(Date mToDoDate) {
        this.mToDoDate = mToDoDate;
    }


    public UUID getIdentifier(){
        return mTodoIdentifier;
    }
}


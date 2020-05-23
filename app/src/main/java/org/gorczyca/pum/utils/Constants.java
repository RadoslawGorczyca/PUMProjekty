package org.gorczyca.pum.utils;

/**
 * Created by: Radosław Gorczyca
 * 25.04.2020 20:00
 */
public class Constants {
    public static final String INTENT_KEY_USING_XML_ARRAYS = "intentKeyUsingXmlArrays";
    public static final int ACTIVITY_FOR_RESULT_ADD_TO_DO_ITEM_REQUEST_CODE = 1;
    public static final String TO_DO_EDITOR_ACTIVITY_MODE = "toDoEditorActivityMode";
    public static final int TO_DO_EDITOR_MODE_ADDING = 1;
    public static final int TO_DO_EDITOR_MODE_EDITING = 2;
    public static final String INTENT_KEY_TO_DO_ITEM_TO_EDIT = "intentKeyToDoItemEdit";
    public static final String INTENT_KEY_TO_DO_ITEM_DETAILS = "toDoItemDetails";
    public static String NEW_TO_DO_TEM_INTENT_EXTRA = "newToDoItemIntentExtra";
    public static String DATE_FORMAT = "dd.MM.yyyy, HH:mm";
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int GALLERY_PICK_IMAGE_REQUEST_CODE = 300;
    public static final int GALLERY_PICK_VIDEO_REQUEST_CODE = 400;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    public enum SORTING_METHOD{
        CREATE_DATE, END_DATE, PRIORITY, NAME, STATUS
    }
}

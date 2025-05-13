package com.at04.touchmovetest;

/**
 * Represents a level entry stored in Firebase. It only holds abstract information about the level -
 * level name, username, timestamp, and id. <br/>
 * TODO: Set id automatically to the next available number.
 */
public class CustomLevelListEntry {
    public static final int BG_LIGHT = 0;
    public static final int BG_DARK = 1;
    public String levelName;
    public String username;
    public String timestamp;
    public String id;
    public String bg_id;
    public CustomLevelListEntry() {}
}

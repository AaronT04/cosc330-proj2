package com.at04.touchmovetest;

/**
 * Represents a level entry stored in Firebase. It only holds abstract information about the level -
 * level name, username, timestamp, and id. <br/>
 * TODO: Set id automatically to the next available number.
 */
public class CustomLevelListEntry {
    public String levelName;
    public String username;
    public String timestamp;
    public String id;
    public CustomLevelListEntry() {}
}

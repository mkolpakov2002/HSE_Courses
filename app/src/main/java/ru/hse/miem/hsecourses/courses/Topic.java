package ru.hse.miem.hsecourses.courses;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Topic {

    @PrimaryKey(autoGenerate = true)
    private int topicId;

    private int moduleNumber;

    private String topicName;

    private String topicInformation;

    private ArrayList<String> topicVideos;

    private ArrayList<String> topicLecture;

    private ArrayList<String> topicTest;

    public Topic(){
        topicVideos = new ArrayList<>();
        topicLecture = new ArrayList<>();
        topicTest = new ArrayList<>();
    }

    public void setModuleNumber(int moduleNumber) {
        this.moduleNumber = moduleNumber;
    }

    public void setTopicInformation(String topicInformation) {
        this.topicInformation = topicInformation;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getModuleNumber() {
        return moduleNumber;
    }

    public String getTopicInformation() {
        return topicInformation;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicLecture(ArrayList<String> topicLecture) {
        this.topicLecture = topicLecture;
    }

    public void setTopicTest(ArrayList<String> topicTest) {
        this.topicTest = topicTest;
    }

    public void setTopicVideos(ArrayList<String> topicVideos) {
        this.topicVideos = topicVideos;
    }

    public ArrayList<String> getTopicLecture() {
        return topicLecture;
    }

    public ArrayList<String> getTopicTest() {
        return topicTest;
    }

    public ArrayList<String> getTopicVideos() {
        return topicVideos;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }
}

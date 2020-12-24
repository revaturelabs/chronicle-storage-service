package com.revature.chronicle;


import com.revature.chronicle.daos.TagDAO;
import com.revature.chronicle.daos.TagDAOImpl;
import com.revature.chronicle.models.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TagDAOTests {
    TagDAO tDAO = new TagDAOImpl();

    /*
    Notes
    -Use @Rollback?
    -Use @BeforeClass and @AfterClass to add t1 and t2 to the database
        -How is this any different in principle from using @FixedMethodOrder?
     */

    @Test
    public void getTagsTest() {
        // use addTag method to add 2 tags
        Tag t1 = new Tag();
        t1.setName("Tag 1 name");
        t1.setValue("Tag 1 value");
        tDAO.addTag(t1);

        Tag t2 = new Tag();
        t2.setName("Tag 2 name");
        t2.setValue("Tag 2 value");
        tDAO.addTag(t2);

        // Set the expected value
        List<Tag> preExpected = new ArrayList<Tag>();
        preExpected.add(t1);
        preExpected.add(t2);
        String expected = preExpected.toString();

        // Use getTags method to get the actual value
        String actual = tDAO.getTags().toString();

        // Compare the expected and actual values

    }

    @Test
    public void getTagByIDTest() {
        // use addTag method to add 2 tags
        Tag t1 = new Tag();
        t1.setName("Tag 1 name");
        t1.setValue("Tag 1 value");
        tDAO.addTag(t1);

        Tag t2 = new Tag();
        t2.setName("Tag 2 name");
        t2.setValue("Tag 2 value");
        tDAO.addTag(t2);

        // Set the expected value
        String expected = t2.toString();

        // Use getTagByID method to get the actual value
        int t2ID = t2.getTagID();
        String actual = tDAO.getTagByID(t2ID).toString();

        // Compare the expected and actual values

    }

    @Test
    public void addTagTest() {
        // Create Tag object to add
        Tag t1 = new Tag();
        t1.setName("Tag 1 name");
        t1.setValue("Tag 1 value");

        Tag t2 = new Tag();
        t2.setName("Tag 2 name");
        t2.setValue("Tag 2 value");

        // Make sure addTag returns true (need to add the AssertEquals part)
        tDAO.addTag(t1);
        tDAO.addTag(t2);

        // Set the value we'd expect when we get all tags
        List<Tag> preExpected = new ArrayList<Tag>();
        preExpected.add(t1);
        preExpected.add(t2);
        String expected = preExpected.toString();

        // Use getTags to get the actual value
        String actual = tDAO.getTags().toString();

        // Compare the expected and actual values
    }

    @Test
    public void updateTagTest() {
        // use addTag method to add a tag
        Tag t1 = new Tag();
        t1.setName("Tag 1 name");
        t1.setValue("Tag 1 value");
        tDAO.addTag(t1);

        // Update the tag's name
        t1.setName("New name");
        tDAO.updateTag(t1);

        // Set the expected value after updating
        String expected = t1.toString();

        // Get the actual value
        int t1ID = t1.getTagID();
        String actual = tDAO.getTagByID(t1.getTagID()).toString();

        // Compare the expected and actual values
    }

    @Test
    public void deleteTagByIDTest() {
        // use addTag method to add 2 tags
        Tag t1 = new Tag();
        t1.setName("Tag 1 name");
        t1.setValue("Tag 1 value");
        tDAO.addTag(t1);

        Tag t2 = new Tag();
        t2.setName("Tag 2 name");
        t2.setValue("Tag 2 value");
        tDAO.addTag(t2);

        // Delete the 1st tag
        int t1ID = t1.getTagID();
        tDAO.deleteTagByID(t1ID);

        // Set the value we'd expect when we get all tags
        List<Tag> preExpected = new ArrayList<Tag>();
        preExpected.add(t2);
        String expected = preExpected.toString();

        // Use getTags to get the actual value
        String actual = tDAO.getTags().toString();

        // Compare the expected and actual values
    }
}

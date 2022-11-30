package utils;

import java.util.ArrayList;
import java.util.Arrays;

public class Csvs {
    private ArrayList<String> urls = new ArrayList<String>();

    public Csvs(String dir) {
        urls.add(dir + "/place_0_0.csv");
        urls.add(dir + "/person_0_0.csv");
        urls.add(dir + "/person_speaks_language_0_0.csv");
        urls.add(dir + "/person_email_emailaddress_0_0.csv");
        urls.add(dir + "/person_knows_person_0_0.csv");
        urls.add(dir + "/organisation_0_0.csv");
        urls.add(dir + "/person_workAt_organisation_0_0.csv");
        urls.add(dir + "/person_studyAt_organisation_0_0.csv");
        urls.add(dir + "/tag_0_0.csv");
        urls.add(dir + "/tagclass_0_0.csv");
        urls.add(dir + "/tag_hasType_tagclass_0_0.csv");
        urls.add(dir + "/tagclass_isSubclassOf_tagclass_0_0.csv");
        urls.add(dir + "/person_hasInterest_tag_0_0.csv");
        urls.add(dir + "/forum_0_0.csv");
        urls.add(dir + "/forum_hasMember_person_0_0.csv");
        urls.add(dir + "/forum_hasTag_tag_0_0.csv");
        urls.add(dir + "/post_0_0.csv");
        urls.add(dir + "/post_hasTag_tag_0_0.csv");
        urls.add(dir + "/person_likes_post_0_0.csv");
        urls.add(dir + "/comment_0_0.csv");
        urls.add(dir + "/comment_hasTag_tag_0_0.csv");
        urls.add(dir + "/person_likes_comment_0_0.csv");

    }

    public ArrayList<String> getUrls(){
        return urls;
    }
}

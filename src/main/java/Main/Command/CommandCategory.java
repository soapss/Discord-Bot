package Main.Command;

import net.dv8tion.jda.api.Permission;

//all command categories
public enum CommandCategory {
    //each category has a string value name and corresponding permissions
    ADMIN("Admin", Permission.ADMINISTRATOR),
    GENERAL("General", Permission.MESSAGE_WRITE),
    MUSIC("Music", Permission.VOICE_CONNECT),
    LEAGUE("League", Permission.MESSAGE_WRITE),
    SEARCH("Search",Permission.MESSAGE_WRITE);

    private final String category;
    private final Permission perm;

    //object
    CommandCategory(String category, Permission perm){
        this.category = category;
        this.perm = perm;
    }

    //get category of a commandcategory object
    public String getCategory(){
        return category;
    }

    //get permission of a commandcategory object
    public Permission getPerm(){
        return perm;
    }
}

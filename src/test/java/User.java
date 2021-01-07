public class User {
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUserDate() {
        return userDate;
    }

    public void setUserDate(int userDate) {
        this.userDate = userDate;
    }

    private String userId;
    private int userDate;
    public User (String userId, int userDate){
        this.userId = userId;
        this.userDate = userDate;
    }
}

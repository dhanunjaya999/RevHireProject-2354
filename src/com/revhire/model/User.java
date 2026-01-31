package com.revhire.model;

public abstract class User {

    protected String email;
    private String password;
    protected UserType userType;

    public User(String email, String password, UserType userType) {
        this.email = email;
        this.setPassword(password);
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public boolean validatePassword(String inputPassword) {
        return getPassword().equals(inputPassword);
    }

    public UserType getUserType() {
        return userType;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

package com.zoinks.waterreporting.model;

import android.util.Log;

import com.github.mikephil.charting.data.Entry;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Facade for the model for users and water reports
 *
 * Created by Nancy on 03/24/2017.
 */

public class Facade {
    public final static String USER_JSON = "users.json";
    public final static String REPORT_JSON = "reports.json";

    // required model classes
    private UserSvcProvider usp;
    private WaterReportSvcProvider wrsp;

    // singleton
    private static Facade instance = new Facade();

    private Facade() {
        usp = new UserSvcProvider();
        wrsp = new WaterReportSvcProvider();
    }

    /**
     * Accessor for singleton instance of Facade
     *
     * @return Facade singleton instance
     */
    public static Facade getInstance() {
        return instance;
    }

    /**
     * Loads data from saved json
     *
     * @param userFile the file from which to load the user json
     * @param reportsFile the file from which to load the reports json
     * @return true if successful
     */
    public boolean loadData(File userFile, File reportsFile) {
        try {  // first load up the users
            BufferedReader input = new BufferedReader(new FileReader(userFile));

            // Since we saved the json as a string, we just read in the string normally
            String inString = input.readLine();
            Log.d("DEBUG", "JSON: " + inString);

            // use the Gson library to recreate the object references and links
            Gson gson = new Gson();
            usp = gson.fromJson(inString, UserSvcProvider.class);

            input.close();
        } catch (IOException e) {
            Log.e("Facade", "Failed to open/read the buffered reader for user json");
            return false;
        }

        try {  // next load up the water reports
            BufferedReader input = new BufferedReader(new FileReader(reportsFile));

            // Since we saved the json as a string, we just read in the string normally
            String inString = input.readLine();
            Log.d("DEBUG", "JSON: " + inString);

            // use the Gson library to recreate the object references and links
            Gson gson = new Gson();
            wrsp = gson.fromJson(inString, WaterReportSvcProvider.class);

            input.close();
        } catch (IOException e) {
            Log.e("Facade", "Failed to open/read the buffered reader for reports json");
            return false;
        }

        return true;
    }

    /**
     * Saves data to json
     *
     * @param userFile the json file to which to save the user
     * @param reportsFile the json file to which to save the reports
     * @return true if successful
     */
    public boolean saveData(File userFile, File reportsFile) {
        try {
            PrintWriter writer = new PrintWriter(userFile);
            Gson gson = new Gson();
            // convert our objects to a string for output
            String outString = gson.toJson(usp);
            Log.d("DEBUG", "JSON Saved: " + outString);
            // then just write the string
            writer.println(outString);
            writer.close();
        } catch (FileNotFoundException e) {
            Log.e("Facade", "Failed to open json file for output");
            return false;
        }

        try {
            PrintWriter writer = new PrintWriter(reportsFile);
            Gson gson = new Gson();
            // convert our objects to a string for output
            String outString = gson.toJson(wrsp);
            Log.d("DEBUG", "JSON Saved: " + outString);
            // then just write the string
            writer.println(outString);
            writer.close();
        } catch (FileNotFoundException e) {
            Log.e("Facade", "Failed to open json file for output");
            return false;
        }

        return true;
    }

    /**
     * Returns the currently logged in user, if any
     *
     * @return the currently logged in user, if any
     */
    public User getCurrentUser() {
        return usp.getCurrentUser();
    }

    /**
     * Gets all of the users currently registered
     * @return a List of all of the Users currently registered
     */
    public List<User> getAllUsers() {
        return usp.getAllUsers();
    }

    /**
     * Attempts to register a new user
     *
     * @param firstName first name of the new user
     * @param lastName last name of the new user
     * @param username username of the new user
     * @param password un-hashed password of new user
     * @param privilege type of new user, used for privilege
     * @return True if the new user was registered, False if the user was not registered (ie username taken)
     */
    public boolean registerUser(String firstName, String lastName, String username, String password,
                            UserType privilege) {
        return usp.register(firstName, lastName, username, password, privilege);
    }

    /**
     * Delete user's account
     *
     * @param username username of the user to delete
     */
    public void deleteUser(String username) {
        usp.deleteUser(username);
    }

    /**
     * Gets the User by username
     * @param username of User to get
     * @return User with associated username
     */
    public User getUserByUsername(String username) {
        return usp.getUserByUsername(username);
    }

    /**
     * Updates attributes passed in to update profile of current user
     *
     * @param oldUsername username from current user
     * @param newUsername username from edit text
     * @param firstName new or old firstName
     * @param lastName new or old lastName
     * @param password "" if password was unchanged, otherwise old hashed password
     * @return boolean true if profile was updated
     */
    public boolean updateUser(String oldUsername, String newUsername, String firstName,
                              String lastName, String password, String email, String address,
                              String phone) {
        return usp.update(oldUsername, newUsername, firstName, lastName, password, email, address,
                phone);
    }

    /**
     * Attempts login
     *
     * @param username username of profile trying to login as
     * @param password to be checked for a match
     * @return True only if user exists and password matches
     *         False if password incorrect, user does not exist, or user blocked
     */
    public boolean login(String username, String password) {
        return usp.login(username, password);
    }

    /**
     * Logs user out of application and resets current user
     */
    public void logout() {
        usp.logout();
    }

    /**
     * Get the list of water reports
     *
     * @return the list of water reports
     */
    public List<WaterReport> getReports() {
        return wrsp.getReports();
    }

    /**
     * Get the list of water source reports
     *
     * @return the list of water source reports
     */
    public List<WaterReport> getSourceReports() {
        return wrsp.getSourceReports();
    }

    /**
     * Get the list of water quality reports for managers to view
     *
     * @return the list of water quality reports
     */
    public List<WaterReport> getQualityReports() {
        return wrsp.getQualityReports();
    }

    /**
     * Adds a water source report where time is current time and author is current user
     *
     * @param latitude latitude of the water report
     * @param longitude longitude of the water report
     * @param type type of water at the water source
     * @param condition condition of water at the water source
     */
    public void addSourceReport(double latitude, double longitude, WaterSourceType type,
                                WaterSourceCondition condition) {
        wrsp.addSourceReport(latitude, longitude, type, condition, usp.getCurrentUser());
    }

    /**
     * Adds a water quality report where time is current time and author is current user
     *
     * @param latitude latitude of the water report
     * @param longitude longitude of the water report
     * @param condition condition of water at the water source
     * @param virusPPM virus concentration measured in PPM at the water source
     * @param contaminantPPM contaminant concentration measured in PPM at the water source
     */
    public void addQualityReport(double latitude, double longitude, WaterQualityCondition condition,
                                 double virusPPM, double contaminantPPM) {
        wrsp.addQualityReport(latitude, longitude, condition, virusPPM, contaminantPPM, usp.getCurrentUser());
    }

    /**
     * Returns the data for the year requested, 12 points, one for each month for the location requested
     *
     * @param year the year from which data is requested
     * @param latitude the latitude of the reports to get
     * @param longitude the longitude of the reports to get
     * @param virus true if the manager wishes to graph virus PPM, false to graph contaminant PPM
     */
    public List<Entry> getYearsData(int year, double latitude, double longitude, boolean virus) {
        return wrsp.getYearsData(year, latitude, longitude, virus);
    }
}

package org.database.dao;

public interface IAccessAccountDAO {
    /***
     * Change a password to person
     * <p>
     * This method it's used when someone needs to change a password
     * to their account.
     * </p>
     * @param password the password to set
     * @param idUser the id user to change password
     * @return
     */
    boolean changePasswordByIdUser(String password, int idUser);

    /***
     * Generate a code by email
     * <p>
     * This method it's used when someone has lost his password and need to recover it
     * generating a new recovery code in database
     * </p>
     * @param emailRecovery the email to generate the code
     * @return true if any row was affected and false if any row was not affected.
     */
    boolean generatePasswordRecoveryCodeByEmail(String emailRecovery);

    /***
     * Change password by email
     * <p>
     * This method it's used when someone has not logged in and needs to change his password
     * </p>
     * @param password the new password to replace de oldpassword
     * @param email the account's email to change it password.
     * @return true if any row was affected and false if any row was no affected.
     */
    boolean changePasswordByEmail(String password, String email);

    /***
     * Get the recovery code by Email
     * <p>
     * This method is used to validate the code sent against the code entered by the user
     * </p>
     * @param emailRecovery the account's email to get the recovery code.
     * @return String representing the recovery code.
     */
    String getRecoveryCodeByEmail(String emailRecovery);

}

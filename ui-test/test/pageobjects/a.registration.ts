import Page from "./page.js";

export default class RegistrationPage extends Page {

async addUser(userEmail: string, password: string) { 
    
    const emailField = await $("input[id = 'email']");
    emailField.isEnabled;
    console.log(emailField.isEnabled);

    await emailField.addValue(userEmail);
    
    const passwordField = await $("input[id = 'password']");
    passwordField.isEnabled;
    console.log(passwordField.isEnabled);

    await passwordField.addValue(password);
    

    const repeatPasswordField = await $("input[id='repeatPassword']")
    repeatPasswordField.isEnabled;
    console.log(repeatPasswordField.isEnabled);

    await repeatPasswordField.addValue(password);
    

    
    const selectID = await $('#roleId');
    const value = await selectID.getValue();
    selectID.isEnabled;
    console.log(selectID.isEnabled);

    await selectID.selectByIndex(0);
   

    const submitButton = await $('//*[@id="form"]/div/form/button');
    submitButton.click();

}
public open(){
    return browser.url(process.env.WEB_APP_URL + "/auth/register" || 'UNDEFINED');
}
}
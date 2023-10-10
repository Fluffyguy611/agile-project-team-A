import Page from "./page.js";

export default class RegistrationPage extends Page {

async addUser() { 
    
    const emailField = await $("input[id = 'email']");
    emailField.isEnabled;
    console.log(emailField.isEnabled);

    await emailField.addValue('Testowy6@kainos.com');
    await browser.pause (5000);
    
    const passwordField = await $("input[id = 'password']");
    passwordField.isEnabled;
    console.log(passwordField.isEnabled);

    await passwordField.addValue('Testowy6@kainos.com');
    await browser.pause(5000);

    const repeatPasswordField = await $("input[id='repeatPassword']")
    repeatPasswordField.isEnabled;
    console.log(repeatPasswordField.isEnabled);

    await repeatPasswordField.addValue('Testowy6@kainos.com');
    await browser.pause(5000);

    
    const selectID = await $('#roleId');
    const value = await selectID.getValue();
    selectID.isEnabled;
    console.log(selectID.isEnabled);

    await selectID.selectByIndex(1);
    await browser.pause(5000);

    const submitButton = await $('//*[@id="form"]/div/form/button');
    submitButton.click();

}
public open(){
    return browser.url(process.env.WEB_APP_URL + "/auth/register" || 'UNDEFINED');
}
}
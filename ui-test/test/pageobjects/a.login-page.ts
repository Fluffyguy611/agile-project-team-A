import Page from "./page.js";

export default class LoginPage extends Page {
    async loginUser() {
        const emailField = await $("input[id='email']");
        emailField.isEnabled;
        console.log(emailField.isEnabled);

        emailField.click;
        await emailField.addValue('Testowy6@kainos.com');
        await browser.pause(5000);

        const passwordField = await $("input[id='password']");
        passwordField.isEnabled;
        console.log(passwordField.isEnabled);

        passwordField.click;
        await passwordField.addValue('Testowy6@kainos.com');
        await browser.pause(5000);

        const submitButton = await $('//*[@id="form"]/div/form/button');
    submitButton.click();
        

    }
    public open(){
        return browser.url(process.env.WEB_APP_URL + "/auth/login" || 'UNDEFINED');
}}
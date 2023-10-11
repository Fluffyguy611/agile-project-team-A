import Page from "./page.js";

export default class AddNewJobRole extends Page {
    async addNewJobRole() {
        const nameField = await $("input[id='name']");
        nameField.isEnabled;
        console.log(nameField.isEnabled);

        nameField.click;
        await nameField.addValue('Test Architekt Level 2');
        await browser.pause(5000);

        const descriptionField = await $("input[id='decription']");
        descriptionField.isEnabled;
        console.log(descriptionField.isEnabled);

        descriptionField.click;
        await descriptionField.addValue('Very important person');
        await browser.pause(5000);

        const sharePointLinkField = await $("input[id='sharePointLink']");
        sharePointLinkField.isEnabled;
        console.log(sharePointLinkField.isEnabled);

        sharePointLinkField.click;
        await sharePointLinkField.addValue('https://kainossoftwareltd.sharepoint.com/:b:/r/people/Job%20Specifications/Engineering/Job%20Profile%20-%20Principal%20Architect%20(Principal).pdf?csf=1&web=1&e=BsKS6e');
        await browser.pause(5000);
        
        const submitButton = await $('//*[@id="form"]/form/button');
    submitButton.click();
        

    }
    public open(){
        return browser.url(process.env.WEB_APP_URL + "/admin/add-jobe-roles" || 'UNDEFINED');
    }
    }


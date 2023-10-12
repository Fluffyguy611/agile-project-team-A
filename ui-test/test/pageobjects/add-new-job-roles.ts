import Page from "./page.js";

export default class AddNewJobRole extends Page {
    async addNewJobRole() {
        const nameField = await $("input[name='name']");
        nameField.isEnabled;
        console.log(nameField.isEnabled);

        nameField.click;
        await nameField.addValue('Test Architekt Level 5');
       

        const descriptionField = await $("textarea[name='description']");
        descriptionField.isEnabled;
        console.log(descriptionField.isEnabled);

        descriptionField.click;
        await descriptionField.addValue('Very important person');
       

        const sharePointLinkField = await $("input[name='sharePointLink']");
        sharePointLinkField.isEnabled;
        console.log(sharePointLinkField.isEnabled);

        sharePointLinkField.click;
        await sharePointLinkField.addValue('https://kainossoftwareltd.sharepoint.com/:b:/r/people/Job%20Specifications/Engineering/Job%20Profile%20-%20Principal%20Architect%20(Principal).pdf?csf=1&web=1&e=BsKS6e');
        
        const submitButton = await $('button[type="submit"]');
    submitButton.click();
        

    }
    public open(){
        return browser.url(process.env.WEB_APP_URL + "/admin/add-job-roles" || 'UNDEFINED');
    }
    }


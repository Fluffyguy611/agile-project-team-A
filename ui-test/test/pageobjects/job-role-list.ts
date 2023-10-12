import Page from "./page.js"

export default class JobRoleListPage extends Page {

    async clickAddRoleDropdown () {
        await $('#navbarDropdown').click();
        const clickAddRole = (await $('a[href="/admin/add-job-roles"]'));
        await clickAddRole.click();

    }
    async getContent(){
        const text = (await $("jobRoles")).getText();
        return text;
    }
    public open(){
        return browser.url(process.env.WEB_APP_URL + "/job-roles" || 'UNDEFINED');
}}
    

import Page from "./page.js"

export default class JobRoleListPage extends Page {
    async getContent(){
        const text = (await $("jobRoles")).getText();
        return text;
    }
    }

import Page from "./page.js";

export default class JobRoleDetailPage extends Page {
    async goToJobRole(index: number) {
        const viewJobRoleDetailPage = await $$("td a");
        await viewJobRoleDetailPage[index].click();
    }
    public open(){
        return browser.url(process.env.WEB_APP_URL + "/job-roles" || 'UNDEFINED');
    }
}
import LoginPage from "../pageobjects/a.login-page.js";
import RegistrationPage from "../pageobjects/a.registration.js";
import AddNewJobRole from "../pageobjects/add-new-job-roles.js";
import JobRoleListPage from "../pageobjects/job-role-list.js";
import JobRoleDetailPage from "../pageobjects/job-roles-detail-page.js";

const jobRoleListPage = new JobRoleListPage();
const jobRoleDetailPage = new JobRoleDetailPage;
const registrationPage = new RegistrationPage;
const loginPage = new LoginPage;
const addNewJobRole = new AddNewJobRole;

const userEmail = "Testowy999@kainos.com";
const password = "Testowy@kainos.com";

describe('Job Role Page', async () => {


    it('go to register page and add user', async () => {
        await registrationPage.open();
        await registrationPage.addUser(userEmail, password);
        
    })
    it('log in user', async () => {
        await loginPage.open();
        await loginPage.loginUser(userEmail, password);
       


    })
    it('go to job role page', async () => {
        const isJobRolesText = await $('//*[@id="roles"]/table/thead/tr/th[1]');
        await jobRoleListPage.open();
        await expect(isJobRolesText).toHaveText('JOB ROLES');


    })
});
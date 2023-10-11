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

describe ('Job Role Page', async () => {

    // it ('go to job role page', async () => {
    //     await jobRoleListPage.open();
    //     await browser.pause(5000);
    // })
    it ('go to register page and add user', async () => {
        await registrationPage.open();
        await registrationPage.addUser();
        await browser.pause(5000);
    })
    it ('log in user', async () => {
        await loginPage.open();
        await loginPage.loginUser();
        await browser.pause(5000);

    } )
    it ('go to add job role page and add job role' ,async () => {
        await addNewJobRole.open();
        await addNewJobRole.addNewJobRole();
        await browser.pause(5000);
    })
    it ('go to view job role page', async () => {
        await jobRoleDetailPage.open();
        await browser.pause(5000);
    })
})
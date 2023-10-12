
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


describe('Add New Job Role Page', async () => {

    it('log in user', async () => {
        await loginPage.open();
        await loginPage.loginUser(userEmail, password);

    })
    it('go to Job roles', async () => {
     
        await jobRoleListPage.clickAddRoleDropdown();
    });
    it('go to add job role page and add job role', async () => {
        await addNewJobRole.addNewJobRole();
      
    })
})
import JobRole from "../model/jobRole.js";

export default class JobRoleValidator {
    validateJobRole(jobRole: JobRole) {

        if (jobRole.name.length > 64) {
            return 'Name longer than 64 characters';
        }

        if (jobRole.description.length > 2000) {
            return 'Description longer than 2000 characters';
        }

        if (jobRole.sharePointLink.length > 2137) {
            return 'SharePointLink longer than 2137 characters';
        }

        return null;
    }
};
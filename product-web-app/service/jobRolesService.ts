import axios from "axios";
import { JobRole } from "../model/jobRole.js";



module.exports.getJobRoles = async function (): Promise<JobRole[]> {
    try {
        const repsonse = await axios.get('http://localhost:8080/api/roles')
        return repsonse.data
    } catch (e) {
        throw new Error ('Could not get job roles')
    }
    
}
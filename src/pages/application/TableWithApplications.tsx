import React, {useState} from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import {Application, ApplicationDataFragment} from '../../graphql/graphql';
import muellicon from '../../icons/muell.png';
import { Link } from "react-router-dom";

interface AllApplications {
    applications: Array<ApplicationDataFragment>
}

function TableWithApplications(props: AllApplications) {
    const [ applicationList, setApplicaitonList ] = useState(props.applications);

    return(
        <table className="table table-striped table-hover table-responsive-sm">
            <tbody>
            {applicationList.map(application => {
                    return <tr key={application.code}>
                        <td id="tdWithCode">{application.code}</td>
                        <td id="tdWithName">
                            <Link to={"/application/" + application.id}>
                                {application.name}
                            </Link>
                        </td>
                        <td id="tdWithImg">
                            <img src={muellicon} alt="Eimer" className="muellIcon"/>
                        </td>
                    </tr>
                }
            )}
            </tbody>
        </table>
    );

}

export default TableWithApplications;
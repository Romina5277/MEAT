import React, {useState} from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import '../../css/Detail.css'
import SubTitleDetail from "./SubTitleDetail";
import { AXAButton } from '../../components/patterns-library';
import {useTranslation} from "react-i18next";

interface ApplicationDetails {
    code: string
    name: string
}

function ApplicationDetailApp(props: ApplicationDetails) {
    const [ application, setApplication ] = useState(props);
    const { t, i18n } = useTranslation();

    return(
        <div id="applicationDetailAppOverAll">
            <SubTitleDetail title="Applikation"/>
            <table className="table table-borderless">
                <tbody>
                    <tr>
                        <td>{t('application.code')}</td>
                        <td>{application.code}</td>
                    </tr>
                    <tr>
                        <td>{t('application.name')}</td>
                        <td>{application.name}</td>
                    </tr>
                </tbody>
            </table>
            <div id="buttonBearbeiten">
                <AXAButton onClick={() => alert("Applikation bearbeiten")}>{t('editing.edit')}</AXAButton>
            </div>
        </div>
    );
}

export default ApplicationDetailApp;
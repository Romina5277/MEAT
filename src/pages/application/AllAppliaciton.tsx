import React, {useEffect, useState} from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import '../../css/AllApplication.css';
import { AXAInputText, AXAButton } from '../../components/patterns-library';
import {ApplicationDataFragment, useApplicationsQuery} from "../../graphql/graphql";
import TableWithApplications from "./TableWithApplications";
import {useTranslation} from "react-i18next";

function AllApplication() {

    const [ suche, setSuche ] = useState("");
    const { data, loading, error } = useApplicationsQuery({
        variables: {},
    });
    const [myApplications, setMyApplications] = useState(Array<ApplicationDataFragment>());
    const { t, i18n } = useTranslation();

    if(data && data.applications){
        const newArray: Array<ApplicationDataFragment> = data.applications.map(applications => applications);
        newArray.forEach(application => {
            if(!myApplications.includes(application)){
                myApplications.push(application)
            }
        });
    }

    function suchen(e: { target: HTMLInputElement}) {
        setSuche(e.target.value)
    }

    function getApplications() {
        if(loading) {
            return <p><b>{t('graphql.load')}</b></p>
        } else if(error) {
            return <p>{error.message}</p>
        } else if(myApplications.length > 0) {
                return <TableWithApplications applications={myApplications}/> //data?.applications}/>
        } else {
            return null
        }
    }

    return(
        <div id="overAll">
            <AXAInputText placeholder={t('searching.searchItem')} name="applicationSuche" value={suche} onChange={suchen}/>
            <br/>
            <div id="searchButton">
                <AXAButton onClick={() => alert(suche)}>{t('searching.search')}</AXAButton>
            </div>
            <br/>
            <div id="applicationsTable">
                {getApplications()}
            </div>
        </div>
    );

}

export default AllApplication;
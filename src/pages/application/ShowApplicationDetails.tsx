import React, {useState} from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import '../../css/Detail.css'
import {useParams} from "react-router";
import ApplicationDetailApp from "../details/ApplicationDetailApp";
import LocationDetail from "../details/LocationDetail";
import MessageDetail from "../details/MessageDetail";
import {useApplicationQuery} from "../../graphql/graphql";
import TitleDetail from "../details/TitleDetail";
import SubTitleDetail from "../details/SubTitleDetail";
import {useTranslation} from "react-i18next";

function ShowApplicationDetails() {

    let { id } = useParams();

    const { data, loading, error } = useApplicationQuery({
        variables: {
            id: id
        }
    });
    const { t, i18n } = useTranslation();

    console.log(id)
/*

    function showLocations() {
        if(data?.application != null) {
            if(data?.application.locations != null) {
                if(data?.application.locations.length > 0) {
                    return <LocationDetail locations={data?.application.locations}/>
                }
            }
        }

        return(
            <div>
                <SubTitleDetail title="Locations"/>
                <p>keine Locations gefunden</p>
            </div>
        )
    }
*/
    if(loading) {
        return <p><b>{t('graphql.load')}</b></p>
    } else if(error) {
        return <p>{error.message}</p>
    } else if(data?.application) {
        return(
            <div id="ShowApplicationDetail">
                <TitleDetail title={data?.application.name}/>
                <div id="MessageDetail">
                    <MessageDetail/>
                </div>
                <div id="AppplicationDetailApp">
                    <ApplicationDetailApp code={data?.application.code} name={data?.application.name}/>
                </div>
                <div id="LocationDetail">

                </div>
            </div>
        );
    } else {
        return null
    }

}

export default ShowApplicationDetails;
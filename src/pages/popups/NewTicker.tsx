import React, {useState} from 'react';
import {useTranslation} from "react-i18next";
import {ApplicationDataFragment, LocationDataFragment, useApplicationsQuery, useLocationQuery} from "../../graphql/graphql";
import Dropdown from 'react-dropdown';
import 'react-dropdown/style.css';

function NewTicker() {
    const { t, i18n } = useTranslation();

    const codes = [''];
    const { data, loading, error } = useApplicationsQuery({
        variables: {},
    });
    const [myApplications, setMyApplications] = useState(Array<ApplicationDataFragment>());
    const [selectedCode, setSelectedCode] = useState('');
    const [applicationId, setApplicationId] = useState('');
    const [locations, setLocations] = useState(Array<LocationDataFragment>());

    if(data && data.applications){
        const newArray: Array<ApplicationDataFragment> = data.applications.map(applications => applications);
        newArray.forEach(application => myApplications.push(application));
    }

    function getApplications() {
        if(loading) {
            return <p><b>{t('graphql.load')}</b></p>
        } else if(error) {
            return <p>{error.message}</p>
        } else if(myApplications.length > 0) {
            myApplications.map(application => {
                if(!codes.includes(application.code)){
                    codes.push(application.code)
                }
            })
            return <Dropdown options={codes} value={selectedCode} onChange={(e) => setSelectedCode(e.value)} placeholder={t('form.application')}/>
        } else {
            return null
        }
    }

    function getLocations() {
        if(selectedCode !== ''){
            myApplications.map(application => {
                /*if (application.code === selectedCode) {
                    setLocations(application.locations);
                }*/
                console.log("Applicationname: " + application.name)
            })
            return <p>Locations: {selectedCode}</p>
        } else {
            return <p>Locations: empty</p>
        }
    }

    return(
        <div>
            <form>
                <input type="text" name="title" placeholder={t('form.title')}/>
                <br/>
                <input type="date" name="timefrom" placeholder={t('form.timeFrom')}/>
                <input type="date" name="timeto" placeholder={t('form.timeTo')}/>
                <br/>
                {/*<input type="text" name="application" placeholder={t('form.application')}/>*/}
                {getApplications()}
                {/*<input type="text" name="location" placeholder={t('form.location')}/>*/}
                {getLocations()}
                <br/>
                <br/>
                <h3>{t('form.texts')}</h3>
                <br/>
                <input type="text" name="sprache" placeholder={t('form.language')}/>
                <input type="textarea" name="text" placeholder={t('form.text')}/>
                <br/>
                <button>{t('form.stop')}</button>
                <button>{t('form.saveAsTicker')}</button>
                <button>{t('form.saveAsTemplate')}</button>
            </form>
        </div>
    );

}

export default NewTicker;
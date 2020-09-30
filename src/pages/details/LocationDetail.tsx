import React, {useState} from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import { Location } from '../../graphql/graphql';

interface LocationDetails {
    locations: Array<Location>
}

function LocationDetail(props: LocationDetails) {
    const [ locations, setLocations ] = useState(props);

    return(
        <div id="locationDetailOverAll">
            <p>LocationDetail</p>
        </div>
    );

}

export default LocationDetail;
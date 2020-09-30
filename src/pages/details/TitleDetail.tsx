import React, {useState} from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import '../../css/Detail.css'

interface TitleObj {
    title: string
}

function TitleDetail(props: TitleObj) {

    return(
        <div>
            <p id="Title"><b>{props.title}</b></p>
        </div>
    );

}

export default TitleDetail;
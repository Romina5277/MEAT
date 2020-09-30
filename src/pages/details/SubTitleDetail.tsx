import React, {useState} from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import '../../css/Detail.css'

interface TitleObj {
    title: string
}

function SubTitleDetail(props: TitleObj) {

    return(
        <div>
            <p id="SubTitle"><b>{props.title}</b></p>
        </div>
    );

}

export default SubTitleDetail;
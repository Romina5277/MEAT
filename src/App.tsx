import React, { useState, Suspense } from 'react';
import './App.css';
import { ApolloProviderContainer } from "./graphql/ApolloProvider";
import { AXAHeading, AXAButtonLink, AXAButton } from './components/patterns-library';
import { BrowserRouter, Route, Switch, Link } from "react-router-dom";
import AllApplication from "./pages/application/AllAppliaciton";
import ShowApplicationDetails from "./pages/application/ShowApplicationDetails";
import AllSamples from "./pages/sample/AllSamples";
import ShowSampleDetails from "./pages/sample/ShowSampleDetails";
import ShowMessageDetail from "./pages/message/ShowMessageDetail";
import { useTranslation } from 'react-i18next';
import NewTicker from "./pages/popups/NewTicker";
import ReactModal from 'react-modal';
import { useModal } from "react-modal-hook";


function App() {
    const [page, setPage] = useState(<AllApplication/>);
//    const [showPopup, setShowPopup] = useState(false);
//    {showPopup ? (<NewTicker/>) : null}
    const { t, i18n } = useTranslation();
    const changeLanguage = (lng: string) => {
        i18n.changeLanguage(lng);
    };

    const [ showModal, hideModal ] = useModal(() => (
        <ApolloProviderContainer>
            <ReactModal isOpen>
                <h2>{t('ticker.title')}</h2>
                <NewTicker/>
                {/*<button onClick={hideModal}>Hide Modal</button>*/}
            </ReactModal>
        </ApolloProviderContainer>
    ));

    /*
    function togglePopup() {
        setShowPopup(!showPopup)
    }
    */

    return (
        <Suspense fallback="loading">
            <ApolloProviderContainer>
                <BrowserRouter>
                    <AXAHeading rank={1} variant={"secondary"} id="header">
                        <p id="navigation">
                            <Link to="/"><AXAButton>{t('application.title')}</AXAButton></Link>
                            <Link to="/samples"><AXAButton>{t('ticker.titleTemplate')}</AXAButton></Link>
                            <AXAButton onClick={showModal}>{t('ticker.new')}</AXAButton>
                        </p>
                        <p id="language">
                            <AXAButton onClick={() => changeLanguage('de')}>{t('language.de')}</AXAButton>
                            <AXAButton onClick={() => changeLanguage('en')}>{t('language.en')}</AXAButton>
                            <AXAButton onClick={() => changeLanguage('fr')}>{t('language.fr')}</AXAButton>
                            <AXAButton onClick={() => changeLanguage('it')}>{t('language.it')}</AXAButton>
                        </p>
                        <p id="title">MEAT</p>
                    </AXAHeading>
                    <div className="App">
                        <Switch>
                            <Route exact path={"/"} component={AllApplication}/>
                            <Route path={"/application/:id"} component={ShowApplicationDetails}/>
                            <Route path={"/samples"} component={AllSamples}/>
                            <Route path={"/sample"} component={ShowSampleDetails}/>
                            <Route path={"/message"} component={ShowMessageDetail}/>
                        </Switch>
                    </div>
                </BrowserRouter>
            </ApolloProviderContainer>
        </Suspense>
    );
}

export default App;

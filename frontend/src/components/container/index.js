import React, {Component} from 'react';
import './style.scss';
import Footer from "../footer";
import Home from "../sites/home";
import {Route} from "react-router-dom";
import LoginPage from "../sites/login";

class Container extends Component {
    render() {
        return (
            <div className={"app"}>
                <div className={"container"}>
                    <Route exact path="/" component={Home}/>
                    <Route exact path="/login" component={LoginPage}/>
                </div>
                <Footer/>
            </div>
        );
    }
}

export default Container;

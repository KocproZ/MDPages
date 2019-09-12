import React, {Component} from 'react';
import './style.scss';
import Footer from "../footer";
import {Route} from "react-router-dom";

import Home from "../sites/home/home";
import Login from "../sites/login";

class Container extends Component {
    render() {
        return (
            <div className={"app"}>
                <Route exact path="/" component={Home}/>
                <Route exact path="/login" component={Login}/>
                <Footer/>
            </div>
        );
    }
}

export default Container;

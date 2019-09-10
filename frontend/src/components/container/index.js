import React, {Component} from 'react';
import './style.scss';
import Footer from "../footer";
import Home from "../sites/home/home";
import {Route} from "react-router-dom";

class Container extends Component {
    render() {
        return (
            <div className={"app"}>
                <div className={"container"}>
                    <Route exact path="/" component={Home}/>
                </div>
                <Footer/>
            </div>
        );
    }
}

export default Container;

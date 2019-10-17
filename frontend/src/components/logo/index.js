import React, {Component} from 'react';
import './style.scss';
import {Link} from "react-router-dom";

class Logo extends Component {
    render() {
        return (
            <Link to={"/"} className={"logo"}/>
        );
    }
}

export default Logo;

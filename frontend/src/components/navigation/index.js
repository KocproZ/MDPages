import React, {Component} from 'react';
import './style.scss';
import Search from "./search/";
import Logo from "../logo";

class Navigation extends Component {
    render() {
        return (
            <div className="navigation">
                <Logo/>
                <Search/>
            </div>
        );
    }
}

export default Navigation;

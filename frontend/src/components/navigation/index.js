import React, {Component} from 'react';
import './style.scss';
import Search from "./search";

class Navigation extends Component {
    render() {
        return (
            <div className="navigation">
                <div className={"logo"}/>
                <Search/>
            </div>
        );
    }
}

export default Navigation;

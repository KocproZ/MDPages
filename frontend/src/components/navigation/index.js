import React, {Component} from 'react';
import './style.scss';
import Search from "./search/";
import Logo from "../logo";

class Navigation extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showNavigation: false,

        };
        this.handleMobileButton = this.handleMobileButton.bind(this);
    }

    handleMobileButton() {
        this.setState({showNavigation: !this.state.showNavigation})
    }

    render() {
        return (
            <div className={"navigation" + (this.state.showNavigation ? " show" : "")}>
                <Logo/>
                <Search/>
                <div className={"mobile-button"}>
                    <input type="checkbox" onChange={this.handleMobileButton} checked={this.state.showNavigation}/>
                    <span/>
                    <span/>
                    <span/>
                </div>
            </div>
        );
    }
}

export default Navigation;

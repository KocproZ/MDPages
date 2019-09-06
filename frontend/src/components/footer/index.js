import React, {Component} from 'react';
import './style.scss';
import Logo from "../logo";

class Footer extends Component {
    render() {
        return (
            <div className={"footer"}>
                <div className={"row"}>
                    <div className={"col-8"}>
                        <div className={"column"}>
                            <ul>
                                <li>MDPages</li>
                                <li><a href={"https://github.com/KocproZ/MDPages"}>About</a></li>
                                <li><a href={"https://github.com/KocproZ/MDPages"}>Docs</a></li>
                                <li><a href={"https://github.com/KocproZ/MDPages"}>GitHub</a></li>
                            </ul>
                            <ul>
                                <li>For</li>
                                <li>Teams</li>
                                <li>Education</li>
                                <li>Writing</li>
                                <li>Documentation</li>
                            </ul>
                            <ul>
                                <li>Links</li>
                                <li><a href={"https://github.com/Starchasers"}>Starchasers</a></li>
                                <li><a href={"https://github.com/KocproZ/MDPages/issues"}>Issues</a></li>
                            </ul>
                        </div>
                    </div>
                    <div className={"col-4 center"}>
                        <Logo/>
                    </div>
                </div>
            </div>
        );
    }
}

export default Footer;

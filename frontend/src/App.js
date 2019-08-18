import React, {Component} from 'react';
import Navigation from "./components/navigation";
import Container from "./components/container";

class App extends Component {
    render() {
        return (
            <div className="App">
                <Navigation/>
                <Container/>
            </div>
        );
    }
}

export default App;

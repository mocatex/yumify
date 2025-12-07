import Light from "./Light.jsx";
import "./TrafficLight.css";
import {useState} from "react";

const STATES = ["red", "yellow", "green"];

export default function TrafficLight({id}) {

    const [activeColor, setActiveColor] = useState("red")

    function nextState() {
        setActiveColor((currentColor) => {
            const nextIndex = STATES.indexOf(currentColor) + 1;
            return STATES[nextIndex % STATES.length];
        })
    }

    return (
        <div id={id} className="traffic-light" onClick={nextState} >
            <Light color="red" active={activeColor === "red"} />
            <Light color="yellow" active={activeColor === "yellow"} />
            <Light color="green" active={activeColor === "green"} />
        </div >
    );
}


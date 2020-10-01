import React from "react";
import { faCloud } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export default () => (
  <>
  <div className="form">
    <h1 className="h1">
      <svg
        width="40px"
        height="40px"
        viewBox="0 0 150 150"
        style={{ top: 9, position: "relative", marginRight: 10 }}
      >
        <defs />
        <g stroke="none" fill="none">
          <g>
          <FontAwesomeIcon icon={faCloud} color="white"/>
              </g>
        </g>  
      </svg>
      Microservices - Transactions App
    </h1>
    </div>
  </>
);

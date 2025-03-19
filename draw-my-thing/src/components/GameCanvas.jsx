import React, { useEffect, useRef, useState } from "react";
import { Col, Form, Row } from "react-bootstrap";
import "./GameCanvas.css";

const GameCanvas = () => {
  const canvasRef = useRef(null);
  const ctxRef = useRef(null);
  const penSizeRef = useRef(20);
  const selectedToolRef = useRef("pen");
  const [color, setColor] = useState("#000000");
  const [penSize, setPenSize] = useState(20);
  const [selectedColor, setSelectedColor] = useState("#000000");
  const [selectedTool, setSelectedTool] = useState("pen");
  const [pos, setPos] = useState({ x: 0, y: 0 });

  const colors = [
    "#FFFFFF",
    "#808080",
    "#FF0000",
    "#FFA500",
    "#FFFF00",
    "#008000",
    "#00FFFF",
    "#0000FF",
    "#800080",
    "#FF00FF",
    "#A52A2A",
    "#000000",
  ];

  const selectTool = (tool) => {
    selectedToolRef.current = tool;
    setSelectedTool(tool);
  };

  const KEY_B = 66;
  const KEY_C = 67;
  const KEY_D = 68;
  const KEY_E = 69;

  const handleKeyDown = (event) => {
    switch (event.keyCode) {
      case KEY_D:
        selectTool("pen");
        break;
      case KEY_E:
        selectTool("eraser");
        break;
      case KEY_B:
        selectTool("bucket");
        break;
      case KEY_C:
        clearCanvas();
        break;
      default:
        break;
    }
  };

  useEffect(() => {
    const canvas = canvasRef.current;
    ctxRef.current = canvas.getContext("2d", { willReadFrequently: true });
    ctxRef.current.fillStyle = "#ffffff";
    ctxRef.current.fillRect(0, 0, canvas.width, canvas.height);
    document.addEventListener("keydown", handleKeyDown);
  }, []);

  useEffect(() => {
    const canvas = canvasRef.current;

    canvas.addEventListener("mousemove", draw);
    canvas.addEventListener("mousedown", setPosition);
    canvas.addEventListener("click", fillArea);

    return () => {
      canvas.removeEventListener("mousemove", draw);
      canvas.removeEventListener("mousedown", setPosition);
      canvas.removeEventListener("click", fillArea);
    };
  }, [pos]);

  const setPosition = (e) => {
    const canvas = canvasRef.current;
    const rect = canvas.getBoundingClientRect();
    const x = e.clientX - rect.left;
    const y = e.clientY - rect.top;

    setPos({ x, y });
    if (
      selectedToolRef.current === "pen" ||
      selectedToolRef.current === "eraser"
    ) {
      ctxRef.current.beginPath();
      ctxRef.current.arc(x, y, penSizeRef.current / 2, 0, Math.PI * 2);
      ctxRef.current.fillStyle = selectedTool === "pen" ? color : "#FFFFFF";
      ctxRef.current.fill();
    }
  };

  const draw = (e) => {
    if (
      e.buttons !== 1 ||
      (selectedToolRef.current !== "pen" &&
        selectedToolRef.current !== "eraser")
    )
      return;

    const canvas = canvasRef.current;
    const rect = canvas.getBoundingClientRect();
    const x = e.clientX - rect.left;
    const y = e.clientY - rect.top;

    ctxRef.current.beginPath();
    ctxRef.current.lineWidth = penSizeRef.current;
    ctxRef.current.lineCap = "round";
    ctxRef.current.strokeStyle =
      selectedToolRef.current === "pen" ? color : "#FFFFFF";

    ctxRef.current.moveTo(pos.x, pos.y);
    ctxRef.current.lineTo(x, y);
    setPos({ x, y });

    ctxRef.current.stroke();
  };

  const clearCanvas = () => {
    const canvas = canvasRef.current;
    ctxRef.current.clearRect(0, 0, canvas.width, canvas.height);
    ctxRef.current.fillStyle = "#ffffff";
    ctxRef.current.fillRect(0, 0, canvas.width, canvas.height);
  };

  const componentToHex = (c) => {
    var hex = c.toString(16);
    return hex.length == 1 ? "0" + hex : hex;
  };

  const rgbToHex = (r, g, b) => {
    return "#" + componentToHex(r) + componentToHex(g) + componentToHex(b);
  };

  const getPixelColor = (ctx, x, y) => {
    const pixel = ctx.getImageData(x, y, 1, 1).data;
    return rgbToHex(pixel[0], pixel[1], pixel[2]);
  };

  const hexToRGB = (hex) => {
    return [
      parseInt(hex.substring(1, 3), 16),
      parseInt(hex.substring(3, 5), 16),
      parseInt(hex.substring(5, 7), 16),
    ];
  };

  const colorMatch = (color1, color2, tolerance = 100) => {
    const rgb1 = hexToRGB(color1);
    const rgb2 = hexToRGB(color2);
    return (
      Math.abs(rgb1[0] - rgb2[0]) <= tolerance &&
      Math.abs(rgb1[1] - rgb2[1]) <= tolerance &&
      Math.abs(rgb1[2] - rgb2[2]) <= tolerance
    );
  };

  const fillArea = (e) => {
    if (selectedToolRef.current !== "bucket") return;

    const canvas = canvasRef.current;
    const rect = canvas.getBoundingClientRect();
    const x = Math.round(e.clientX - rect.left);
    const y = Math.round(e.clientY - rect.top);
    const oldColor = getPixelColor(ctxRef.current, x, y);

    if (colorMatch(oldColor, color, 10)) return;

    floodFill(ctxRef.current, x, y, oldColor, color);
  };

  const floodFill = (ctx, startX, startY, oldColor, newColor) => {
    const canvas = ctx.canvas;
    const w = canvas.width;
    const h = canvas.height;
    const imageData = ctx.getImageData(0, 0, w, h);
    const data = imageData.data;

    const getColorAt = (x, y) => {
      const index = (y * w + x) * 4;
      return rgbToHex(data[index], data[index + 1], data[index + 2]);
    };

    const setColorAt = (x, y, rgb) => {
      const index = (y * w + x) * 4;
      data[index] = rgb[0];
      data[index + 1] = rgb[1];
      data[index + 2] = rgb[2];
      data[index + 3] = 255; // Fully opaque
    };
    const newRGB = hexToRGB(newColor);

    const stack = [[startX, startY]];
    while (stack.length) {
      const [x, y] = stack.pop();

      if (x < 0 || y < 0 || x >= w || y >= h) continue;
      if (!colorMatch(getColorAt(x, y), oldColor)) continue;

      setColorAt(x, y, newRGB);

      stack.push([x + 1, y]);
      stack.push([x - 1, y]);
      stack.push([x, y + 1]);
      stack.push([x, y - 1]);
    }

    ctx.putImageData(imageData, 0, 0);
  };

  const handleSliderChange = (e) => {
    const newSize = parseInt(e.target.value, 10);
    setPenSize(newSize);
    penSizeRef.current = newSize;
  };

  const tools = [
    { name: "pen", label: "Pen", icon: "../../pen.png", shortcut: "D" },
    {
      name: "bucket",
      label: "Bucket",
      icon: "../../bucket.png",
      shortcut: "B",
    },
    {
      name: "eraser",
      label: "Eraser",
      icon: "../../eraser.png",
      shortcut: "E",
    },
  ];

  return (
    <div className="canvas-container">
      <canvas ref={canvasRef} width={800} height={600} className="canvas" />
      <Row>
        <Col md={8}>
          {" "}
          <div className="controls-container">
            <div className="palet-tools-container">
              {" "}
              <div className="color-palette">
                {colors.map((color, index) => (
                  <div
                    key={index}
                    className={`color-box ${
                      selectedColor === color
                        ? selectedColor === "#000000"
                          ? "selected"
                          : "selected-color"
                        : ""
                    }`}
                    style={{ backgroundColor: color }}
                    onClick={() => {
                      setColor(color);
                      setSelectedColor(color);
                    }}
                  />
                ))}
              </div>
              <div className="buttons-container">
                {tools.map((tool) => (
                  <span
                    className="d-inline-block"
                    tabIndex="0"
                    data-toggle="tooltip"
                    title={`Shortcut: ${tool.shortcut} key`}
                    key={tool.name}
                  >
                    <div
                      key={tool.name}
                      className={`button ${
                        selectedTool === tool.name ? "selected" : ""
                      }`}
                      onClick={() => selectTool(tool.name)}
                    >
                      <div className="button-text">{tool.label}</div>
                      <img
                        className="p-1"
                        width="30"
                        height="30"
                        src={tool.icon}
                        alt={tool.label}
                      />
                    </div>
                  </span>
                ))}
                <span
                  className="d-inline-block"
                  tabIndex="0"
                  data-toggle="tooltip"
                  title="Shortcut: C key"
                >
                  <div className="button" onClick={clearCanvas}>
                    <div className="button-text">Clear All</div>
                    <img
                      className="p-1"
                      width="30"
                      height="30"
                      src="../../bin.png"
                      alt="Clear All"
                    />
                  </div>
                </span>
              </div>
              ;
            </div>
          </div>
        </Col>
        <Col md={4}>
          {" "}
          <Form.Label>Pen Size</Form.Label>
          <Form.Range
            value={penSize}
            min={15}
            max={40}
            onChange={handleSliderChange}
            className="custom-slider"
          />
          <div className="pen-preview-container">
            <div
              style={{
                width: `${penSize}px`,
                height: `${penSize}px`,
                borderRadius: "50%",
                backgroundColor:
                  selectedToolRef.current !== "eraser" ? color : "#FFFFFF",
                border: "1px solid #000",
              }}
            ></div>
          </div>
        </Col>
      </Row>
    </div>
  );
};

export default GameCanvas;

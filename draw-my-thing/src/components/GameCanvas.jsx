import React, { useEffect, useRef, useState } from "react";
import { Button } from "react-bootstrap";
import "./GameCanvas.css";

const GameCanvas = () => {
  const canvasRef = useRef(null);
  const [ctx, setCtx] = useState(null);
  const [color, setColor] = useState("black");
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

  useEffect(() => {
    const canvas = canvasRef.current;
    const context = canvas.getContext("2d");
    setCtx(context);

    document.addEventListener("mousemove", draw);
    document.addEventListener("mousedown", handleMouseDown);
    document.addEventListener("mouseenter", handleMouseDown);

    return () => {
      document.removeEventListener("mousemove", draw);
      document.removeEventListener("mousedown", handleMouseDown);
      document.removeEventListener("mouseenter", handleMouseDown);
    };
  }, [pos, ctx]);

  const setPosition = (e) => {
    const canvas = canvasRef.current;
    const rect = canvas.getBoundingClientRect();
    setPos({
      x: e.clientX - rect.left,
      y: e.clientY - rect.top,
    });
  };

  const draw = (e) => {
    if (e.buttons !== 1 || selectedTool !== "pen") return;

    const canvas = canvasRef.current;
    const rect = canvas.getBoundingClientRect();
    const x = e.clientX - rect.left;
    const y = e.clientY - rect.top;

    ctx.beginPath();
    ctx.lineWidth = 5;
    ctx.lineCap = "round";
    ctx.strokeStyle = color;

    ctx.moveTo(pos.x, pos.y);
    setPosition(e);
    ctx.lineTo(x, y);

    ctx.stroke();
  };

  const handleMouseDown = (e) => {
    if (selectedTool === "bucket") {
      const canvas = canvasRef.current;
      const rect = canvas.getBoundingClientRect();
      const x = e.clientX - rect.left;
      const y = e.clientY - rect.top;
      // Get the color of the clicked pixel
      const pixelColor = ctx.getImageData(x, y, 1, 1).data;
      // Convert the selected color to RGBA format
      const fillColor = hexToRgbA(color);
      if (
        pixelColor[0] !== fillColor[0] ||
        pixelColor[1] !== fillColor[1] ||
        pixelColor[2] !== fillColor[2]
      ) {
        floodFill(ctx, x, y, pixelColor, fillColor);
      }
    }
    setPosition(e);
  };

  const clearCanvas = () => {
    const canvas = canvasRef.current;
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.fillStyle = "#ffffff";
    ctx.fillRect(0, 0, canvas.width, canvas.height);
  };

  const floodFill = (ctx, x, y, targetColor, fillColor) => {
    const canvas = ctx.canvas;
    const imageData = ctx.getImageData(0, 0, canvas.width, canvas.height);
    const pixelStack = [[x, y]];
    const targetR = targetColor[0];
    const targetG = targetColor[1];
    const targetB = targetColor[2];
    console.log(pixelStack);

    while (pixelStack.length) {
      const [currentX, currentY] = pixelStack.pop();
      const pixelPos = (currentY * canvas.width + currentX) * 4;

      if (
        currentX < 0 ||
        currentX >= canvas.width ||
        currentY < 0 ||
        currentY >= canvas.height
      )
        continue;

      const r = imageData.data[pixelPos];
      const g = imageData.data[pixelPos + 1];
      const b = imageData.data[pixelPos + 2];

      if (r === targetR && g === targetG && b === targetB) {
        imageData.data[pixelPos] = fillColor[0]; // Red
        imageData.data[pixelPos + 1] = fillColor[1]; // Green
        imageData.data[pixelPos + 2] = fillColor[2]; // Blue
        imageData.data[pixelPos + 3] = fillColor[3]; // Alpha

        pixelStack.push([currentX + 1, currentY]);
        pixelStack.push([currentX - 1, currentY]);
        pixelStack.push([currentX, currentY + 1]);
        pixelStack.push([currentX, currentY - 1]);
      }
    }

    ctx.putImageData(imageData, 0, 0);
  };

  const hexToRgbA = (hex) => {
    let r = 0,
      g = 0,
      b = 0,
      a = 255;
    if (hex.length === 7) {
      r = parseInt(hex.slice(1, 3), 16);
      g = parseInt(hex.slice(3, 5), 16);
      b = parseInt(hex.slice(5, 7), 16);
    } else if (hex.length === 4) {
      r = parseInt(hex[1] + hex[1], 16);
      g = parseInt(hex[2] + hex[2], 16);
      b = parseInt(hex[3] + hex[3], 16);
    }
    return [r, g, b, a];
  };

  return (
    <div className="canvas-container">
      {selectedTool}
      <canvas ref={canvasRef} width={800} height={600} className="canvas" />
      <div className="controls-container">
        <div className="color-palette">
          {colors.map((color, index) => (
            <div
              key={index}
              className="color-box"
              style={{ backgroundColor: color }}
              onClick={() => setColor(color)}
            />
          ))}
        </div>
        <div className="buttons-container">
          <Button
            className="button"
            onClick={() => {
              setSelectedTool("pen");
            }}
          >
            Pen
            <img
              className="p-1"
              width={"30px"}
              height={"30px"}
              src="../../pen.png"
              alt="Pen"
            />
          </Button>
          <Button
            className="button"
            onClick={() => {
              setSelectedTool("bucket");
            }}
          >
            Bucket
            <img
              className="p-1"
              width={"30px"}
              height={"30px"}
              src="../../bucket.png"
              alt="Bucket"
            />
          </Button>
          <Button
            className="button"
            onClick={() => {
              setColor("#FFFFFF");
            }}
          >
            Eraser
            <img
              className="p-1"
              width={"30px"}
              height={"30px"}
              src="../../eraser.png"
              alt="Eraser"
            />
          </Button>
          <Button
            className="button"
            onClick={() => {
              clearCanvas();
            }}
          >
            Clear All
            <img
              className="p-1"
              width={"30px"}
              height={"30px"}
              src="../../bin.png"
              alt="Clear All"
            />
          </Button>
        </div>
      </div>
    </div>
  );
};

export default GameCanvas;

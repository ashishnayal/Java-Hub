"use client";

import { useState } from "react";
import type { CodeExample } from "@/lib/api";

interface CodeBlockProps {
  example: CodeExample;
}

export default function CodeBlock({ example }: CodeBlockProps) {
  const [copied, setCopied] = useState(false);
  const [isExecuting, setIsExecuting] = useState(false);
  const [executionResult, setExecutionResult] = useState<{output: string, error: string, exitCode: number} | null>(null);

  async function handleCopy() {
    try {
      await navigator.clipboard.writeText(example.code);
      setCopied(true);
      setTimeout(() => setCopied(false), 2000);
    } catch {
      // fallback
      const textarea = document.createElement("textarea");
      textarea.value = example.code;
      document.body.appendChild(textarea);
      textarea.select();
      document.execCommand("copy");
      document.body.removeChild(textarea);
      setCopied(true);
      setTimeout(() => setCopied(false), 2000);
    }
  }

  async function handleRun() {
    setIsExecuting(true);
    setExecutionResult(null);
    try {
      const res = await fetch("http://localhost:8080/api/compile", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ code: example.code }),
      });
      const data = await res.json();
      setExecutionResult(data);
    } catch (err) {
      setExecutionResult({ output: "", error: "Failed to connect to compiler API", exitCode: -1 });
    } finally {
      setIsExecuting(false);
    }
  }

  // Simple syntax highlighting for Java
  function highlightCode(code: string): string {
    const keywords = [
      "public", "private", "protected", "class", "interface", "enum", "record",
      "sealed", "non-sealed", "permits", "extends", "implements", "abstract",
      "final", "static", "void", "return", "new", "this", "super", "if", "else",
      "for", "while", "do", "switch", "case", "default", "break", "continue",
      "try", "catch", "finally", "throw", "throws", "import", "package",
      "instanceof", "var", "yield", "module", "requires", "exports", "when",
      "true", "false", "null", "int", "long", "double", "float", "boolean",
      "char", "byte", "short", "String", "Integer", "Double", "Long",
      "List", "Map", "Set", "Optional", "Stream", "Thread",
      "synchronized", "volatile"
    ];

    let highlighted = code
      // Escape HTML
      .replace(/&/g, "&amp;")
      .replace(/</g, "&lt;")
      .replace(/>/g, "&gt;");

    // Comments (// and /* */)
    highlighted = highlighted.replace(
      /(\/\/.*$)/gm,
      '<span class="comment">$1</span>'
    );
    highlighted = highlighted.replace(
      /(\/\*[\s\S]*?\*\/)/g,
      '<span class="comment">$1</span>'
    );

    // Strings
    highlighted = highlighted.replace(
      /(&quot;|")((?:(?!\1)[\s\S])*?)\1/g,
      '<span class="string">"$2"</span>'
    );

    // Triple-quoted text blocks
    highlighted = highlighted.replace(
      /(&quot;&quot;&quot;|""")([\s\S]*?)\1/g,
      '<span class="string">"""$2"""</span>'
    );

    // Annotations
    highlighted = highlighted.replace(
      /@(\w+)/g,
      '<span class="annotation">@$1</span>'
    );

    // Numbers
    highlighted = highlighted.replace(
      /\b(\d+\.?\d*[fFdDlL]?)\b/g,
      '<span class="number">$1</span>'
    );

    // Keywords
    const keywordPattern = new RegExp(
      `\\b(${keywords.join("|")})\\b`,
      "g"
    );
    highlighted = highlighted.replace(
      keywordPattern,
      (match) => {
        // Don't highlight inside already-highlighted spans
        return `<span class="keyword">${match}</span>`;
      }
    );

    return highlighted;
  }

  return (
    <div className="code-block-wrapper">
      <div className="code-block-header">
        <div style={{ display: "flex", alignItems: "center", gap: "12px" }}>
          <span className="code-block-title">{example.title}</span>
          <span className="code-block-lang">{example.language}</span>
        </div>
        <div style={{ display: "flex", gap: "8px" }}>
          {example.language.toLowerCase() === 'java' && (
            <button
              className="code-block-run"
              style={{ background: "var(--accent-primary)", color: "white", padding: "4px 8px", borderRadius: "4px", fontSize: "0.85rem", cursor: "pointer", border: "none" }}
              onClick={handleRun}
              disabled={isExecuting}
            >
              {isExecuting ? "⏳ Running..." : "▶ Run"}
            </button>
          )}
          <button
            className={`code-block-copy ${copied ? "copied" : ""}`}
            onClick={handleCopy}
          >
            {copied ? "✓ Copied" : "📋 Copy"}
          </button>
        </div>
      </div>
      <div className="code-block-body">
        <pre>
          <code
            dangerouslySetInnerHTML={{
              __html: highlightCode(example.code),
            }}
          />
        </pre>
      </div>
      {example.explanation && (
        <div className="code-block-explanation">
          💡 {example.explanation}
        </div>
      )}
      {executionResult && (
        <div className="code-block-execution" style={{ padding: "12px", borderTop: "1px solid var(--border-color)", backgroundColor: "#000", fontFamily: "monospace", color: "#0f0" }}>
          <div style={{ color: "#fff", marginBottom: "8px", fontSize: "0.85rem" }}>Execution Result (Exit Code: {executionResult.exitCode}):</div>
          {executionResult.output && <pre style={{ margin: 0, whiteSpace: "pre-wrap" }}>{executionResult.output}</pre>}
          {executionResult.error && <pre style={{ margin: 0, color: "#f55", whiteSpace: "pre-wrap" }}>{executionResult.error}</pre>}
        </div>
      )}
    </div>
  );
}

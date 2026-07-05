"use client";

import { useState } from "react";
import type { CodeExample } from "@/lib/api";

interface CodeBlockProps {
  example: CodeExample;
}

export default function CodeBlock({ example }: CodeBlockProps) {
  const [copied, setCopied] = useState(false);

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
        <button
          className={`code-block-copy ${copied ? "copied" : ""}`}
          onClick={handleCopy}
        >
          {copied ? "✓ Copied" : "📋 Copy"}
        </button>
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
    </div>
  );
}

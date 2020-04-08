(function() {
	if ("undefined" === typeof window.NTKF) {
		var l = window,
			v = document.documentElement || document.body,
			y = window.location,
			x = l.navigator,
			m = x.userAgent.toLowerCase(),
			j, d = {
				extend: function() {
					var a = arguments[0] || {},
						b = 1,
						c = arguments.length,
						e, f, d;
					"object" !== typeof a && "function" !== typeof a && (a = {});
					c === b && (a = this, --b);
					for (; b < c; b++)
						if (null != (e = arguments[b]))
							for (f in e) d = e[f], a !== d && void 0 !== d && (a[f] = d, a[f] && ("object" == typeof a[f] && "undefined" ==
								typeof a[f].extend) && (a[f].extend = this.extend));
					return a
				}
			};
		d.extend({
			PRESENCE: null,
			loadedfCACHE: 0,
			defaultConfig: null,
			globalConfig: {
				islogin: 1,
				trailID: "",
				destid: "",
				destsid: "",
				newwindow: !1,
				machineid: "",
				statictis: 2,
				CONNECT_LEVEL: 0,
				notrail: 0,
				userlevel: 0,
				itemid: "",
				itemparam: "",
				proinfo: null,
				orderid: "",
				orderprice: "",
				entrance: 0,
				pagetype: "",
				params: "",
				fixed: !0,
				single: 0,
				waitSendMessage: null,
				userToken: "",
				chattype: "",
				chatvalue: ""
			},
			user: {
				name: "",
				id: "",
				logo: "",
				streamId: "",
				shortID: ""
			}
		});
		d.extend({
			Flash: function() {
				var a = !1,
					b = [0, 0, 0, 0],
					c = function(a) {
						var e = b,
							a = (a || "0.0.0").split(".").slice(0, 3);
						return parseInt(e[0]) >
							parseInt(a[0]) || parseInt(e[0]) == parseInt(a[0]) && parseInt(e[1]) > parseInt(a[1]) || parseInt(e[0]) ==
							parseInt(a[0]) && parseInt(e[1]) == parseInt(a[1]) && parseInt(e[2]) > parseInt(a[2])
					};
				if (x.plugins && x.mimeTypes.length) {
					var e = x.plugins["Shockwave Flash"];
					e && e.version ? (a = !0, b = e.version.split(".")) : e && e.description && (a = !0, b = e.description.replace(
						/([a-zA-Z]|\s)+/, "").replace(/(\s+r|\s+b[0-9]+)/, ".").split("."), 3 == b.length && (b[b.length] = 0))
				} else if (m && 0 <= m.indexOf("Windows CE"))
					for (var e = 1, f = 3; e;) try {
						f++, e = new ActiveXObject("ShockwaveFlash.ShockwaveFlash." +
							f), a = !0, b = [f, 0, 0]
					} catch (h) {
						e = null
					} else {
						try {
							e = new ActiveXObject("ShockwaveFlash.ShockwaveFlash")
						} catch (g) {}
						null != e && (a = !0, b = e.GetVariable("$version").split(" ")[1].toString().replace(/,/g, ".").split("."))
					}
				return {
					remove: function(a) {
						d.$(a).each(function(a) {
							if (d.B.IE) {
								for (var b in a) try {
									"function" == typeof a[b] && eval("el." + b + "=null")
								} catch (e) {}
								b = document.createElement("DIV");
								b.appendChild(a);
								b.innerHTML = "";
								try {
									b.parentNode.removeChild(b)
								} catch (c) {}
							} else try {
								a.parentNode.removeChild(a)
							} catch (f) {}
						})
					},
					supportFlash: (0 ==
							b.join(".").indexOf("11.6.602") || 0 == b.join(".").indexOf("11.3.300") || !c("10.3")) && /firefox/.test(m) ?
						!1 : a,
					version: b.join("."),
					GT: c
				}
			}()
		});
		d.extend({
			register: function(a, b) {
				for (var c = a.split("."), e = d, f = null; f = c.shift();)
					if (c.length) void 0 === e[f] && (e[f] = {}), e = e[f];
					else if (void 0 === e[f]) try {
					return e[f] = b, !0
				} catch (h) {
					throw h.description;
				} else break;
				return !1
			},
			fn: {},
			$: function(a, b, c) {
				var e, f = "nt" + d.createTimeID(1);
				a ? "string" == typeof a ? e = d._joQuery.query(a, b) : d.isArray(a) ? e = a : d.isObject(a) ? (e = a.tagName ||
					"div",
					a.tagName && delete a.tagName, a = d.A.extend(a, {
						_ntid: f
					}), e = [d.D.creElm(a, e, d.isArray(b) ? b.first() : b ? b : null, c)]) : d.isElement(a) && (e = [a]) : e = [];
				return e = d.A.extend(e, d.fn)
			},
			isDefined: function(a) {
				return "undefined" !== typeof a
			},
			isFunction: function(a) {
				return "[object Function]" === Object.prototype.toString.call(a)
			},
			isArray: function(a) {
				return "[object Array]" === Object.prototype.toString.call(a)
			},
			isObject: function(a) {
				return "[object Object]" === Object.prototype.toString.call(a) && !this.isDefined(a.nodeType)
			},
			isElement: function(a) {
				return /\[object\dwindow|document|element\]/.test(Object.prototype.toString.call(a).toLowerCase()) ||
					this.isDefined(a.nodeType)
			},
			isEmptyObject: function(a) {
				for (var b in a) return !1;
				return !0
			},
			isURL: function(a) {
				return RegExp(
					"^((https|http|ftp|rtsp|mms)?://)?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?(([0-9]{1,3}.){3}[0-9]{1,3}|([0-9a-z_!~*'()-]+.)*([0-9a-z][0-9a-z-]{0,61})?[0-9a-z].[a-z]{2,6})(:[0-9]{1,4})?((/?)|(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$",
					"i").test(a)
			},
			_joQuery: {
				tagGuid: 1,
				attrMap: {
					"class": "className",
					"for": "htmlFor"
				},
				rex: {
					R_RULE: /[ +>~]/g,
					NR_RULE: /[^ +>~]+/g,
					TRIM_LR: /^ +| +$/g,
					TRIM_ALL: / *([ +>~,]) */g,
					PSEU_PARAM: /\([^()]+\)/g,
					ATTR_PARAM: /[^\[]+(?=\])/g,
					ATTR: /=|!=|\^=|\$=|\*=|~=|\|=/,
					CLS: /\./g,
					PSEU: /[^:]+/g,
					NUM: /\d+/,
					NTH: /(-?\d*)n([+-]?\d*)/,
					RULES: /((?:#.+)*)([a-zA-Z*]*)([^\[:]*)((?:\[.+\])*)((?::.+)*)/
				},
				query: function(a, b) {
					if ("undefined" == typeof a || !a) return [];
					var c = [],
						e, f, d, g, i, k, r;
					switch (typeof b) {
						case "undefined":
							f = [document];
							break;
						case "string":
							a = b + " " + a;
							f = [document];
							break;
						case "object":
							f = b.nodeType ? [b] : b
					}
					e = this.format(a);
					b = f;
					g = 0;
					for (i = e.length; g < i; g++) {
						a = e[g];
						d = (" " +
							a).match(this.rex.R_RULE);
						a = a.match(this.rex.NR_RULE);
						f = b;
						k = 0;
						for (r = d.length; k < r; k++) f = this.parse(a[k], f, d[k]);
						c = c.concat(f)
					}
					return 1 < i ? this.makeDiff(c) : c
				},
				format: function(a) {
					var b = [],
						c = [];
					this.pseuParams = b;
					this.attrParams = c;
					for (a = a.replace(this.rex.TRIM_LR, "").replace(this.rex.TRIM_ALL, "$1").replace(this.rex.ATTR_PARAM,
							function(a) {
								return c.push(a) - 1
							}); - 1 !== a.indexOf("(");) a = a.replace(this.rex.PSEU_PARAM, function(a) {
						return b.push(a.substring(1, a.length - 1)) - 1
					});
					return a.split(",")
				},
				parse: function(a,
					b, c) {
					var e, a = this.rex.RULES.exec(a);
					if (e = a[1]) return (e = document.getElementById(e.substring(1))) ? [e] : [];
					b = d._Relative[c](b, a[2] || "*", this);
					if (c = a[3]) b = this.filterClass(b, c.replace(this.rex.CLS, ""));
					if (c = a[4]) b = this.filterAttr(b, this.getAttrRules(c.match(this.rex.ATTR_PARAM), this.attrParams));
					if (a = a[5]) b = this.filterPseudo(b, this.getPseudoRules(a.match(this.rex.PSEU), this.pseuParams));
					return b
				},
				getRules: function(a) {
					var b, a = this.rex.RULES.exec(a);
					a[2] || (a[2] = "*");
					a[3] = a[3].replace(this.rex.CLS, "");
					if (b =
						a[4]) a[4] = this.getAttrRules(b.match(this.rex.ATTR_PARAM), this.attrParams);
					if (b = a[5]) a[5] = this.getPseudoRules(b.match(this.rex.PSEU), this.pseuParams);
					return a
				},
				getAttrRules: function(a, b) {
					for (var c = [], e = a.length, f = this.rex.ATTR, h = 0, g, i; h < e; h++) g = b[a[h]], i = g.match(f) || " ",
						g = g.split(f), c.push(d._Attrs[i]), c.push(g);
					return c
				},
				getPseudoRules: function(a, b) {
					for (var c = [], e = 0, f = a.length, h = this.tagGuid++, g, i, k; e < f; e++) {
						g = a[e];
						if (this.rex.NUM.test(g)) switch (i = b[RegExp["$&"]], g = RegExp["$`"], g) {
							case "nth-child":
								if (this.rex.NTH.test("odd" ===
										i && "2n+1" || "even" === i && "2n" || i)) {
									if (i = RegExp.$1, "" === i ? i = 1 : "-" === i ? i = -1 : i *= 1, i = [h, !0, i, 1 * RegExp.$2], 1 ===
										i[2] && 0 === i[3]) continue
								} else i = [h, !1, 1 * i];
								break;
							case "not":
								k = i.split(",");
								for (i = []; k.length;) i.push(this.getRules(k.pop()))
						}
						c.push(d._Pseudos[g]);
						c.push(i)
					}
					return c
				},
				filterPseudo: function(a, b) {
					for (var c = a.length, e = 0, f = b.length, d = [], g, i, k, r; e < c; e++) {
						i = a[e];
						for (g = 0; g < f && !(k = b[g], r = b[g + 1], !k(i, r, this)); g += 2);
						g === f && d.push(i)
					}
					return d
				},
				filterAttr: function(a, b) {
					for (var c = a.length, e = 0, f = b.length, d = [],
							g, i, k, r, w, z; e < c; e++) {
						i = a[e];
						for (g = 0; g < f; g += 2) {
							r = b[g];
							k = b[g + 1];
							z = k[0];
							if (!(w = "href" === z ? i.getAttribute(z, 2) : i.getAttribute(z)))
								if (!(w = i[this.attrMap[z] || z])) break;
							if (!r(w + "", k[1])) break
						}
						g === f && d.push(i)
					}
					return d
				},
				filterClass: function(a, b) {
					for (var c = 0, e = a.length, f = [], d, g, i; c < e; c++)
						if (i = a[c], (d = i.className) && "string" == typeof d) g = RegExp(d.replace(" ", "|"), "g"), b.replace(g,
							"") || f.push(i);
					return f
				},
				filterEl: function(a, b, c, e, f) {
					return "*" !== b && a.nodeName.toLowerCase() !== b || c && !this.filterClass([a], c).length ||
						e && !this.filterAttr([a], e).length || f && !this.filterPseudo([a], f).length ? !1 : !0
				},
				makeDiff: function(a) {
					for (var b = this.tagGuid++, c = a.length, e = [], f = 0, d, g; f < c; f++) d = a[f], g = this.getElData(d), g
						.tagGuid !== b && (e.push(d), g.tagGuid = b);
					return e
				},
				getElData: function(a) {
					var b = a.mojoExpando;
					b || (b = a.mojoExpando = {
						mQuery: {
							tagGuid: 0
						}
					});
					if (!(b = b.mQuery)) b = {
						tagGuid: 0
					};
					return b
				}
			},
			_Relative: {
				" ": function(a, b, c) {
					for (var e = c.tagGuid++, f = a.length, d = [], g = 0, i, k, r; g < f; g++) {
						i = a[g];
						if (k = i.parentNode) {
							if (c.getElData(k).tagGuid === e) continue;
							c.getElData(i).tagGuid = e
						}
						r = i.getElementsByTagName(b);
						i = 0;
						for (k = r.length; i < k; i++) d.push(r[i])
					}
					return d
				},
				">": function(a, b) {
					for (var c = [], e = a.length, f = 0, d; f < e; f++)
						for (d = a[f].firstChild; d;) 1 === d.nodeType && (d.nodeName.toLowerCase() === b || "*" === b) && c.push(d),
							d = d.nextSibling;
					return c
				},
				"+": function(a, b) {
					for (var c = [], e = a.length, f = 0, d; f < e; f++)
						for (d = a[f]; d = d.nextSibling;)
							if (1 === d.nodeType) {
								(d.nodeName.toLowerCase() === b || "*" === b) && c.push(d);
								break
							} return c
				},
				"~": function(a, b, c) {
					for (var e = c.tagGuid++, f = a.length, d = [], g = 0, i, k; g < f; g++) {
						i = a[g];
						if (k = i.parentNode) {
							if ((k = c.getElData(k)).tagGuid === e) continue;
							k.tagGuid = e
						}
						for (; i = i.nextSibling;) 1 === i.nodeType && (i.nodeName.toLowerCase() === b || "*" === b) && d.push(i)
					}
					return d
				}
			},
			_Attrs: {
				" ": function() {
					return !0
				},
				"=": function(a, b) {
					return a === b
				},
				"!=": function(a, b) {
					return a !== b
				},
				"^=": function(a, b) {
					return 0 === a.indexOf(b)
				},
				"$=": function(a, b) {
					return a.substring(a.length - b.length) === b
				},
				"*=": function(a, b) {
					return -1 !== a.indexOf(b)
				},
				"~=": function(a, b) {
					return -1 !== (" " + a + " ").indexOf(" " +
						b + " ")
				},
				"|=": function(a, b) {
					return a === b || a.substring(0, b.length + 1) === b + "-"
				}
			},
			_Pseudos: {
				"first-child": function(a) {
					for (; a = a.previousSibling;)
						if (1 === a.nodeType) return !1;
					return !0
				},
				"last-child": function(a) {
					for (; a = a.nextSibling;)
						if (1 === a.nodeType) return !1;
					return !0
				},
				"only-child": function(a) {
					for (var b = a.nextSibling, a = a.previousSibling; b;) {
						if (1 === b.nodeType) return !1;
						b = b.nextSibling
					}
					for (; a;) {
						if (1 === a.nodeType) return !1;
						a = a.previousSibling
					}
					return !0
				},
				"nth-child": function(a, b, c) {
					var e, f, d;
					if ((e = a.parentNode) &&
						(d = c.getElData(e)).tagGuid !== b[0]) {
						e = e.firstChild;
						for (f = 1; e;) 1 === e.nodeType && (c.getElData(e).nodeIndex = f++), e = e.nextSibling;
						d.tagGuid = b[0]
					}
					a = c.getElData(a).nodeIndex;
					return b[1] ? (a -= b[3], b = b[2], 0 <= a * b && 0 === a % b) : a === b[2]
				},
				not: function(a, b, c) {
					for (var e = 0, f = b.length, d; e < f; e++) {
						d = b[e];
						if (d[1]) {
							if ("#" + a.id !== d[1]) continue;
							return !1
						}
						if (c.filterEl(a, d[2], d[3], d[4], d[5])) return !1
					}
					return !0
				},
				enabled: function(a) {
					return !1 === a.disabled
				},
				disabled: function(a) {
					return !0 === a.disabled
				},
				checked: function(a) {
					return !0 ===
						a.checked
				},
				empty: function(a) {
					return !a.firstChild
				}
			}
		});
		d.extend({
			A: {
				inArray: function(a, b) {
					if (void 0 == a.length)
						for (var c in a) {
							if (a[c] == b) return c
						} else {
							c = 0;
							for (var e = a.length; c < e; c++)
								if (a[c] == b) return c
						}
					return !1
				},
				toURI: function(a, b, c) {
					var e = [],
						f = void 0 === c ? "&" : c,
						c = function(e, c) {
							var g = "&" == f ? e + "=" : "";
							switch (typeof a[h]) {
								case "object":
									return c ? g + d.J.toJSONString(c) : b ? "" : g;
								case "function":
								case "undefined":
									return "";
								case "boolean":
								case "number":
									return g + c;
								default:
									return c ? g + encodeURIComponent(c) : b ? "" : g
							}
						};
					if (!a) return "";
					if (void 0 == a.length)
						for (var h in a) {
							var g = c(h, a[h]);
							g && e.push(g)
						} else
							for (var i = 0, k = a.length; i < k; i++)(g = c(i, a[i])) && e.push(g);
					return e.join(f)
				},
				extend: function() {
					var a = arguments[0] || {},
						b = arguments;
					if (1 >= b.length) return a;
					for (var c = 1; c < b.length; c++)
						if (b[c] && null != b[c])
							if (void 0 == b[c].length)
								for (var e in b[c]) a[e] = b[c][e];
							else
								for (var f = 0, d = b[c].length; f < d; f++) void 0 != a.length ? a.push(b[c][f]) : a[f] = b[c][f];
					return a
				},
				each: function(a, b) {
					delete a.toJSONString;
					if (void 0 == a.length)
						for (var c in a) b.apply(this,
							[a[c], c]);
					else {
						c = 0;
						for (var e = a.length; c < e; c++) b.apply(this, [a[c], c])
					}
					return a
				},
				wget: function(a, b, c, e) {
					return d.A.extend(a, d.A.get(b, c, e))
				},
				get: function(a, b, c, e) {
					var f = {};
					if (b)
						for (var h = 0, g = b.length; h < g; h++) c && c.length == b.length ? f[c[h]] = a[b[h]] : f[b[h]] = a[b[h]];
					else f = a;
					if (!d.isFunction(e)) return f;
					var a = {},
						i;
					for (i in f) "function" != typeof f[i] && (a[i] = e.call(this, f[i]));
					return a
				},
				argumentsToArray: function(a) {
					for (var b = [], c = 0; c < a.length; c++) b.push(a[c]);
					return b
				}
			},
			B: function() {
				var a = window.external ||
					"",
					b, c, e, f, d, g, i, k = function(a) {
						var b = 0;
						return parseFloat(a.replace(/\./g, function() {
							return 1 == b++ ? "" : "."
						}))
					};
				b = /msie/.test(m) ? parseInt(/\d+/.exec(m.split(";")[1])[0]) : 0;
				try {
					if (/windows|win32/i.test(m) ? i = "windows" : /macintosh/i.test(m) ? i = "macintosh" : /rhino/i.test(m) ? i =
						"rhino" : (/android/i.test(m) || /linux/i.test(m)) && (i = "android"), (f = m.match(/applewebkit\/([^\s]*)/)) &&
						f[1] || (f = m.match(/browser\/applewebkit\/([^\s]*)/)) && f[1] ? (e = "webkit", g = k(f[1])) : (f = m.match(
							/presto\/([\d.]*)/)) && f[1] ? (e = "presto", g = k(f[1])) :
						(f = m.match(/msie\s([^;]*)/)) ? (e = "trident", g = 1, (f = m.match(/trident\/([\d.]*)/)) && f[1] && (g = k(
							f[1]))) : /gecko/.test(m) && (e = "gecko", g = 1, (f = m.match(/rv:([\d.]*)/)) && f[1] && (g = k(f[1]))), c =
						m.match(/(maxthon|theworld|360se|360ee|se|greenbrowser|tencenttraveler|qqbrowser)\b[ \/]?([\w\.]*)/i)) d = c.slice(
						1);
					else if (/maxthon/.test(m) || "number" == typeof a.max_version) d = ["maxthon", a.max_version]
				} catch (r) {}
				return {
					UA: m,
					OS: i,
					CORE: e,
					Version: g,
					EXTRA: d || !1,
					IE: /msie/.test(m),
					Firefox: /firefox/.test(m),
					OPERA: /opera/.test(m),
					MOZ: /gecko/.test(m) && !/(compatible|webkit)/.test(m),
					IE5: /msie 5 /.test(m),
					IE55: /msie 5.5/.test(m),
					IE6: /msie 6/.test(m),
					IE7: /msie 7/.test(m),
					IE8: /msie 8/.test(m),
					IE9: /msie 9/.test(m),
					OLDIE: /msie/.test(m) && 9 > b,
					IE_VER: b,
					SAFARI: !/chrome\/([\d.]*)/.test(m) && /\/([\d.]*) safari/.test(m),
					SAFARI2: !/chrome\/([\d.]*)/.test(m) && /\/([\d.]*) safari/.test(m) && 522 > parseInt(g) && !/adobeair/i.test(
						m),
					CHROME: /chrome\/([\d.]*)/.test(m),
					IPAD: /\(ipad/i.test(m),
					IPHONE: /\(iphone/i.test(m),
					ITOUCH: /\(itouch/i.test(m),
					MOBILE: /mobile/i.test(m) ||
						"android" == i,
					CSS1Compat: "CSS1Compat" == document.compatMode
				}
			}(),
			C: {
				cache: null,
				load: function(a, b, c, e) {
					function f(a, b) {
						var e, c = /\.css(?:\?|$)/i.test(b),
							f = /\.((gif)|(png)|(jpg)|(bmp)|(jpeg))$/i.test(b) || /#image$/ig.test(b),
							b = b.replace(/(#image)|(#buffer)|(#rnd)$/ig, "");
						if (c) e = document.createElement("link"), e.type = "text/css", e.rel = "stylesheet", e.href = b;
						else {
							if (f) return q._image({
								src: b,
								onload: function(b) {
									q.cache[a].status = 0 < b.width || 0 < b.height ? "complete" : "abnormal";
									q.cache[a].loadtime = d.createTimeID() - q.cache[a].loadtime;
									q.cache[a].onload.call(q, b)
								},
								onerror: function(b) {
									q.cache[a].status = "error";
									q.cache[a].loadtime = d.createTimeID() - q.cache[a].loadtime;
									q.cache[a].onload.call(q, b)
								}
							});
							e = document.createElement("script");
							e.type = "text/javascript";
							e.async = "async";
							e.src = b
						}
						e.charset = "utf-8";
						e[d.B.OLDIE ? "onreadystatechange" : "onload"] = e.onerror = function(a) {
							/^(?:loaded|complete|undefined)$/.test(e.readyState) && (e.onload = e.onerror = e.onreadystatechange = null,
								q.cache[k].status = "error" != (a || l.event).type ? "complete" : "error", q.cache[k].loadtime =
								d.createTimeID() - q.cache[k].loadtime, q.cache[k].onload.call(q, k), setTimeout(function() {
									try {
										g && j.removeChild(e)
									} catch (a) {}
								}, 500))
						};
						e.onchange = function() {};
						try {
							return j.insertBefore(e, j.lastChild)
						} catch (h) {
							try {
								return j.appendChild(e)
							} catch (i) {}
						}
					}
					var h = e && e.verification ? !0 : !1,
						g = !e || e && !1 == e.remove ? !1 : !0,
						e = "",
						i, k, r, w, j, m = [],
						q = this,
						p = function(a) {
							for (var b in q.cache)
								if ("function" != typeof q.cache[b] && (q.cache[b].uri == a || -1 < q.cache[b].uri.indexOf(a))) return q.cache[
									b];
							return !1
						};
					if (!a || "object" == typeof a &&
						!d.isArray(a)) a && d.debug.Log("params abnormal", 2);
					else {
						"function" != typeof b && (c = b, b = new Function);
						j = c || document.getElementsByTagName("head")[0] || document.documentElement;
						this.cache = this.cache || {};
						a = d.isArray(a) ? a : [a];
						i = a.length;
						for (var u = 0; u < a.length; u++) /#rnd$/ig.test(a[u]) && (e = "ts=" + d.createTimeID()), c = -1 < a[u].indexOf(
								"?") ? -1 < a[u].indexOf("#") ? a[u].replace(/#/i, "&" + e + "#") : a[u] + (e ? "&" + e : "") : -1 < a[u].indexOf(
								"#") ? a[u].replace(/#/i, "?" + e + "#") : a[u] + (e ? "?" + e : ""), w = d.S.formatDate(0, "d h:i:s MS"),
							r = h ? p(a[u]) :
							p(c.replace(/(#image)|(#buffer)|(#rnd)$/ig, "")), !1 !== r && !/#buffer$/ig.test(a[u]) ? (b.call(this, r, r.element),
								i--) : "" == c || e == c ? (this.cache[w] = {
								uri: "",
								status: "abnormal"
							}, b.call(this, this.cache[w], null), i--) : (k = w + "_" + d.randomChar() + "_" + c.replace(
								/.*\/(.*?)(\?.*)?$/i, "$1"), this.cache[k] = {
								uri: c.replace(/(#image)|(#buffer)|(#rnd)$/ig, ""),
								status: "loading",
								loadtime: d.createTimeID(),
								onload: function(a) {
									m.push(q.cache[k]);
									m.length == i && b.call(q, 1 == m.length ? m[0] : m, a)
								}
							}, this.cache[k].element = f(k, c))
					}
				},
				_iQueue: [],
				_iCache: [],
				_iTime: null,
				_image: function(a) {
					var b = this;
					if (d.isArray(a.src)) {
						for (var c = a.oncomplete, e = 0; e < a.src.length; e++) this._iQueue.push({
							src: a.src[e],
							onerror: a.onerror,
							onload: a.onload
						});
						this._iTime = setInterval(function() {
							var a;
							if (1 == b._iQueue.length) clearInterval(b._iTime), a = d.A.extend(b._iQueue.shift(), {
								oncomplete: c
							});
							else {
								if (0 == b._iQueue.length) {
									clearInterval(b._iTime);
									return
								}
								a = b._iQueue.shift()
							}
							b._image(a)
						}, 20)
					} else return e = d.createTimeID(), this._iCache[e] = new Image, this._iCache[e].src = a.src,
						this._iCache[e].onerror = function() {
							a.onerror && a.onerror.call(this, this)
						}, this._iCache[e].onload = function() {
							a.onload && a.onload.call(this, this)
						}, a.oncomplete && a.oncomplete.call(this, this), this._iCache[e]
				},
				ready: function(a, b) {
					a[d.B.OLDIE ? "onreadystatechange" : "onload"] = a.onerror = function(c) {
						c = c || l.event;
						/^(?:loaded|complete|undefined)$/.test(a.readyState) && (a.onload = a.onerror = a.onreadystatechange = null,
							b.apply(this, [a, a.readyState || c.type]))
					}
				}
			},
			Color: {
				antiColor: function(a) {
					a = this.toRGB(a);
					return this.toHex({
						r: 255 -
							a.r,
						g: 255 - a.g,
						b: 255 - a.b
					})
				},
				isHex: function(a) {
					return "string" == typeof a && /^#?([0-9a-f]{3}|[0-9a-f]{6})$/ig.test(a)
				},
				isRGB: function(a) {
					return d.isObject(a) && d.isDefined(a.r) && d.isDefined(a.g) && d.isDefined(a.b)
				},
				isHSL: function(a) {
					return d.isObject(a) && d.isDefined(a.h) && d.isDefined(a.s) && d.isDefined(a.l)
				},
				toRGB: function(a) {
					return this.isHex(a) ? this.hex2RGB(a) : this.isHSL(a) ? this.hsl2RGB(a) : a
				},
				toHSL: function(a) {
					return this.isHex(a) ? this.rgb2HSL(this.hex2RGB(a)) : this.isRGB(a) ? this.rgb2HSL(a) : a
				},
				toHex: function(a) {
					return this.isRGB(a) ?
						this.rgb2Hex(a) : this.isHSL(a) ? this.rgb2Hex(this.hsl2RGB(a)) : a
				},
				hex2RGB: function(a) {
					var a = a.toString().replace("#", ""),
						b = a.split(""),
						c = {};
					3 == a.length ? c = {
						r: parseInt(b[0] + b[0], 16),
						g: parseInt(b[1] + b[1], 16),
						b: parseInt(b[2] + b[2], 16)
					} : 6 == a.length ? c = {
						r: parseInt(b[0] + b[1], 16),
						g: parseInt(b[2] + b[3], 16),
						b: parseInt(b[4] + b[5], 16)
					} : (d.debug.Log("no hex color value", 3), c = {
						r: 0,
						g: 0,
						b: 0
					});
					return c
				},
				rgb2Hex: function(a) {
					var b = "#",
						c;
					for (c in a)
						if (!(-1 == "rgb".indexOf(c) || "function" == typeof a[c])) {
							var e = parseInt(a[c]).toString(16);
							2 != e.length && (e = "0" + e);
							b += e
						} return b
				},
				rgb2HSL: function(a) {
					var b, c, e, f, d, g = {};
					b = a.r / 255;
					c = a.g / 255;
					a = a.b / 255;
					e = Math.min(b, c, a);
					f = Math.max(b, c, a);
					d = f - e;
					g.l = (f + e) / 2;
					0 == d ? (g.h = 0, g.s = 0) : (g.s = 0.5 > g.l ? d / (f + e) : d / (2 - f - e), del_R = ((f - b) / 6 + d / 2) /
						d, del_G = ((f - c) / 6 + d / 2) / d, del_B = ((f - a) / 6 + d / 2) / d, b == f ? g.h = del_B - del_G : c ==
						f ? g.h = 1 / 3 - del_B + del_R : a == f && (g.h = 2 / 3 - del_R + del_G), 0 > g.h && (g.h += 1), 1 < g.h &&
						(g.h -= 1));
					return g
				},
				hsl2RGB: function(a) {
					var b, c, e = {
						r: 0,
						g: 0,
						b: 0
					};
					if (!a) return e;
					0 == a.s ? (e.r = 255 * a.l, e.g = 255 * a.l, e.b = 255 * a.l) : (c =
						0.5 > a.l ? a.l * (1 + a.s) : a.l + a.s - a.s * a.l, b = 2 * a.l - c, e.r = 255 * this.Hue_2_RGB(b, c, a.h +
							1 / 3), e.g = 255 * this.Hue_2_RGB(b, c, a.h), e.b = 255 * this.Hue_2_RGB(b, c, a.h - 1 / 3));
					e.r = Math.max(0, Math.min(e.r, 255));
					e.g = Math.max(0, Math.min(e.g, 255));
					e.b = Math.max(0, Math.min(e.b, 255));
					return e
				},
				Hue_2_RGB: function(a, b, c) {
					0 > c && (c += 1);
					1 < c && (c -= 1);
					return 1 > 6 * c ? a + 6 * (b - a) * c : 1 > 2 * c ? b : 2 > 3 * c ? a + 6 * (b - a) * (2 / 3 - c) : a
				}
			},
			Cookie: {
				enable: function() {
					var a = !1;
					if (!d.B.IE && navigator.cookieEnabled) return !0;
					this.set("testcookie", "yes", 0);
					"yes" == this.get("testcookie") &&
						(a = !0);
					this.del("testcookie");
					return a
				},
				set: function(a, b, c, e) {
					e = e ? e : d.domain;
					"number" != typeof c && !(c instanceof Date) && (c = 864E5);
					var f;
					c instanceof Date ? f = (f = c) ? "expires=" + f.toGMTString() + "; " : "" : 0 == c ? f = d.B.IE ? " " :
						"expires=0; " : 0 < c && (f = new Date, f.setTime(f.getTime() + c), f = f ? "expires=" + f.toGMTString() +
							"; " : "");
					e = "domain=" + e + "; ";
					a = a + "=" + encodeURIComponent(b) + "; ";
					document.cookie = a + f + e + "path=/; ";
					return b
				},
				del: function(a, b) {
					return this.set(a, "", new Date, b)
				},
				get: function(a) {
					var b = document.cookie;
					return b.length &&
						(a = RegExp("(?:^|;)\\s*" + a + "=(.*?)(?:;|$)").exec(b)) && a.length ? decodeURIComponent(a[1]) : null
				},
				onlyOne: function(a, b, c) {
					if ("1" === this.get(a)) return !1;
					var e = new Date,
						d = new Date;
					switch (b) {
						case "nd":
							12 > e.getHours() ? e.setDate(e.getDate()) : e.setDate(e.getDate() + c);
							e.setHours(12);
							e.setMinutes(0);
							e.setSeconds(0);
							break;
						case "d":
							e.setDate(e.getDate() + c);
							e.setHours(0);
							e.setMinutes(0);
							e.setSeconds(0);
							break;
						case "h":
							e.setHours(e.getHours() + c);
							e.setMinutes(0);
							e.setSeconds(0);
							break;
						case "m":
							e.setMinutes(e.getMinutes() +
								c);
							e.setSeconds(0);
							break;
						case "s":
							e.setSeconds(e.getSeconds() + c);
							break;
						default:
							this.set(a, "1");
							return
					}
					b = e.getTime() - d.getTime();
					this.set(a, "1", b);
					return !0
				}
			},
			D: {
				debug: !1,
				creElm: function(a, b, c, e) {
					var f = document.body || document.documentElement,
						h, b = b || "div";
					if ("undefined" != typeof d.DOMContentLoaded && !d.DOMContentLoaded) {
						if (e = (c || f).firstChild)
							for (; 1 != e.nodeType;) e = e.nextSibling;
						if ("comment" == b) h = "<\!--" + a.text + "--\>";
						else {
							h = "<" + b;
							for (var g in a) "function" != typeof a[g] && (h += " " + ("classname" == g.toLowerCase() ?
								"class" : g) + '="' + a[g] + '"');
							h += "></" + b + ">"
						}
						return e ? this.insert(e, h, "beforebegin") : this.insert(c || f, h, "afterbegin")
					}
					if ("comment" == b) b = document.createComment(a.text);
					else
						for (g in b = document.createElement(b), a) "function" != typeof a[g] && ("style" == g ? b[g].cssText = a[g] :
							0 == g.indexOf("_") ? b.setAttribute(g, a[g]) : b[g] = a[g]);
					return (c || f).insertBefore && !1 !== e ? (c || f).insertBefore(b, e || (c || f).firstChild) : (c || f).appendChild(
						b)
				},
				addHTML: function(a, b) {
					if (d.B.IE) a.insertAdjacentHTML("BeforeEnd", b);
					else {
						var c = a.ownerDocument.createRange();
						c.setStartBefore(a);
						c = c.createContextualFragment(b);
						a.appendChild(c)
					}
				},
				insert: function(a, b, c) {
					c = c ? c.toLowerCase() : "afterbegin";
					if (a.insertAdjacentHTML) {
						switch (c) {
							case "beforebegin":
								return a.insertAdjacentHTML("BeforeBegin", b), a.previousSibling;
							case "afterbegin":
								return a.insertAdjacentHTML("AfterBegin", b), a.firstChild;
							case "afterend":
								return a.insertAdjacentHTML("AfterEnd", b), a.nextSibling;
							default:
								return a.insertAdjacentHTML("BeforeEnd", b), a.lastChild
						}
						throw 'Illegal insertion point -> "' + c + '"';
					}
					var e =
						a.ownerDocument.createRange();
					switch (c) {
						case "beforebegin":
							return e.setStartBefore(a), b = e.createContextualFragment(b), a.parentNode.insertBefore(b, a), a.previousSibling;
						case "afterbegin":
							return a.firstChild ? (e.setStartBefore(a.firstChild), b = e.createContextualFragment(b), a.insertBefore(b,
								a.firstChild)) : a.innerHTML = b, a.firstChild;
						case "beforeend":
							return a.lastChild ? (e.setStartAfter(a.lastChild), b = e.createContextualFragment(b), a.appendChild(b)) : a
								.innerHTML = b, a.lastChild;
						case "afterend":
							return e.setStartAfter(a),
								b = e.createContextualFragment(b), a.parentNode.insertBefore(b, a.nextSibling), a.nextSibling
					}
					throw 'Illegal insertion point -> "' + c + '"';
				},
				scrollLeft: function(a, b) {
					var c = a == document || !a ? document : a.ownerDocument;
					return "undefined" == typeof b ? c.body.scrollLeft || c.documentElement.scrollLeft : d.B.CSS1Compat && !d.B.CHROME ?
						c.documentElement.scrollLeft = b : c.body.scrollLeft = b
				},
				scrollTop: function(a, b) {
					var c = a == document || !a ? document : a.ownerDocument;
					return "undefined" == typeof b ? c.body.scrollTop || c.documentElement.scrollTop :
						d.B.CSS1Compat && !d.B.CHROME ? c.documentElement.scrollTop = b : c.body.scrollTop = b
				},
				rect: function(a) {
					var b = a.parentNode,
						c = document.getElementsByTagName("body")[0],
						e = a,
						f = a.offsetParent,
						h = a.ownerDocument,
						g = "fixed" == d.D.css(a, "position"),
						i = 0,
						k = 0,
						r = 0,
						w = 0;
					if (a.getBoundingClientRect) b = a.getBoundingClientRect(), i = b.left + this.scrollLeft(a), k = b.top + this.scrollTop(
						a), i -= h.documentElement.clientLeft, k -= h.documentElement.clientTop;
					else {
						i = a.offsetLeft;
						for (k = a.offsetTop; f;) {
							i += f.offsetLeft;
							k += f.offsetTop;
							if (d.B.MOZ &&
								!/^t(able|d|h)$/i.test(f.tagName) || d.B.SAFARI && !d.B.SAFARI2) i += parseInt(d.D.css(f, "borderLeftWidth")) ||
								0, k += parseInt(d.D.css(f, "borderLeftTop")) || 0;
							!g && "fixed" == d.D.css(f, "position") && (g = !0);
							e = /^body$/i.test(f.tagName) ? e : f;
							f = f.offsetParent
						}
						for (; b && b.tagName && !/^body|html$/i.test(b.tagName);) /^inline|table.*$/i.test(d.D.css(b, "display")) ||
							(i -= b.scrollLeft, k -= b.scrollTop), d.B.MOZ && "visible" != d.D.css(b, "overflow") && (i += parseInt(d.D.css(
									b, "borderLeftWidth")) || 0, k += parseInt(d.D.css(b, "borderLeftTop")) ||
								0), b = b.parentNode;
						if (d.B.SAFARI2 && (g || "absolute" == d.D.css(e, "position")) || d.B.MOZ && "absolute" != d.D.css(e,
								"position")) i -= h.body.offsetLeft, k -= h.body.offsetTop;
						g && (i += this.scrollLeft(a), k += this.scrollTop(a))
					}
					1 !== c.offsetTop && (i -= parseInt(d.D.css(a, "margin-left")) || 0, k -= parseInt(d.D.css(a, "margin-top")) ||
						0);
					try {
						r = (a.offsetWidth || 0) + (document.body == a && !d.B.CSS1Compat && d.B.IE6 ? -17 : 0), w = a.offsetHeight ||
							0
					} catch (j) {}
					return {
						left: i,
						top: k,
						width: r,
						height: w
					}
				},
				clientRect: function(a) {
					a = "string" == typeof a ? this.$(a).get(0) :
						a;
					if (!a) return {
						left: 0,
						top: 0,
						width: 0,
						height: 0
					};
					var b = this.rect(a),
						c = this.scrollLeft(a),
						e = this.scrollTop(a),
						f = document.body,
						h = document.documentElement;
					b.left -= c;
					b.top -= e;
					a === f && (a = d.B.CSS1Compat && d.B.IE6 ? f.clientTop : h.clientTop, c = j ? 17 : 0, b.viewX = (l.pageXOffset ||
						h.scrollLeft || f.scrollLeft) - a, b.viewY = (l.pageYOffset || h.scrollTop || f.scrollTop) - a, b.viewW = (
						l.innerWidth || h.clientWidth || f.clientWidth || 0) - c, b.viewH = (l.innerHeight || h.clientHeight || f.clientHeight ||
						0) - 0);
					return b
				},
				attr: function(a, b, c) {
					if (a) return "undefined" !=
						typeof c ? ("" === c ? a.removeAttribute(b) : a.setAttribute(b, c), c) : a.getAttribute(b)
				},
				css: function(a, b, c) {
					b = d.S.camelize(b);
					if (void 0 === c)
						if (d.B.IE) switch (c = a.currentStyle ? a.currentStyle[b] : null, b) {
							case "opacity":
								b = 100;
								try {
									b = a.filters["DXImageTransform.Microsoft.Alpha"].opacity
								} catch (e) {
									try {
										b = a.filters("alpha").opacity
									} catch (f) {}
								}
								return b / 100;
							case "float":
								b = "styleFloat";
							case "height":
								return "auto" == c ? "0px" : a.style[b];
							case "width":
								return "auto" == c ? "0px" : a.style[b];
							default:
								return c = a.currentStyle ? a.currentStyle[b] :
									null, a.style[b] || c
						} else {
							"float" == b && (b = "cssFloat");
							try {
								var h = document.defaultView.getComputedStyle(a, "")
							} catch (g) {}
							return a.style[b] || (h ? "auto" == h[b] ? "0" : h[b] : null)
						} else {
							if (d.B.IE) switch (b) {
								case "opacity":
									a.style.filter = 1 <= parseInt(c) ? "none" : "alpha(opacity=" + 100 * c + ")";
									if (!a.currentStyle || !a.currentStyle.hasLayout) a.style.zoom = 1;
									break;
								case "float":
									b = "styleFloat"
							} else "float" == b && (b = "cssFloat");
							try {
								a.style[b] = "NaNpx" == c ? "0px" : c
							} catch (i) {}
						}
				},
				addClass: function(a, b) {
					if (!this.indexOfClass(a, b)) {
						var c = a.className.split(/\s+/).join(" ");
						a.className = (c ? c + " " : "") + b;
						return !0
					}
					return !1
				},
				removeClass: function(a, b) {
					a && 0 < a.className.length && (a.className = a.className.replace(RegExp("(^|\\s+)" + b + "(\\s+|$)", "i"),
						" "))
				},
				replaceClass: function(a, b, c) {
					this.addClass(a, c);
					this.removeClass(a, b)
				},
				indexOfClass: function(a, b) {
					return !a || !a.className ? !1 : RegExp("(^|\\b)" + b + "(\\s|$)", "gi").test(a.className)
				},
				existsScroll: function(a) {
					if (d.B.IE) return !1;
					v = d.B.CSS1Compat && !d.B.CHROME ? document.documentElement : document.body;
					var a = a || "scrollTop",
						b = v[a],
						c = !1;
					v[a] +=
						0 < b ? -1 : 1;
					v[a] !== b && (c = c || !0);
					v[a] = b;
					return c
				},
				animate: function(a, b, c, e, f) {
					var a = !d.isArray(a) ? [a] : a,
						h = {
							complete: f || !f && e || d.isFunction(c) && c,
							duration: c,
							easing: f && e || e && e.constructor != Function && e
						};
					h.duration = (h.duration && h.duration.constructor == Number ? h.duration : {
						slow: 600,
						fast: 200
					} [h.duration]) || 400;
					return d.A.each(a, function(a) {
						if (!a || !d.D.queue.add(a) || 1 != a.nodeType) return !1;
						for (var e in b) {
							if ("height" == e || "width" == e) h.display = d.D.css(a, "display"), h.overflow = d.D.css(a, "overflow");
							d.isArray(b[e]) &&
								((h.specialEasing = h.specialEasing || {})[e] = b[e][1], h[e] = b[e] = b[e][0])
						}
						h.curAnim = b;
						d.A.each(b, function(b, e) {
							var c = new d.D.animExt(a, h, e),
								f = b.toString().match(/^([+-]=?)?([\d+-.]+)(.*)$/),
								i = parseFloat(d.D.css(a, e)) || 0;
							switch (e.toLowerCase()) {
								case "scrollleft":
									i = a.scrollLeft;
									break;
								case "scrolltop":
									i = a.scrollTop;
									break;
								case "opacity":
									i *= 100
							}
							if (f) {
								var j = parseFloat(f[2]),
									m = f[3] || "px";
								f[1] && (j = (-1 < f[1].indexOf("-") ? -1 : 1) * j + (-1 < f[1].indexOf("=") ? 0 : i));
								c.custom(i, j, m)
							} else c.custom(i, b, "")
						})
					})
				},
				easing: {
					linear: function(a,
						b, c, e) {
						return c + e * a
					},
					swing: function(a, b, c, e) {
						return (-Math.cos(a * Math.PI) / 2 + 0.5) * e + c
					},
					easeOutCubic: function(a, b, c, e, d) {
						return e * ((b = b / d - 1) * b * b + 1) + c
					},
					easeInOutCubic: function(a, b, c, e, d) {
						return 1 > (b /= d / 2) ? e / 2 * b * b * b + c : e / 2 * ((b -= 2) * b * b + 2) + c
					},
					easeOutQuart: function(a, b, c, e, d) {
						return -e * ((b = b / d - 1) * b * b * b - 1) + c
					},
					easeOutQuint: function(a, b, c, e, d) {
						return e * ((b = b / d - 1) * b * b * b * b + 1) + c
					},
					easeInOutQuint: function(a, b, c, e, d) {
						return 1 > (b /= d / 2) ? e / 2 * b * b * b * b * b + c : e / 2 * ((b -= 2) * b * b * b * b + 2) + c
					},
					easeInOutElastic: function(a, b, c, e, d) {
						var a =
							1.70158,
							h = 0,
							g = e;
						if (0 == b) return c;
						if (2 == (b /= d / 2)) return c + e;
						h || (h = d * 0.3 * 1.5);
						g < Math.abs(e) ? (g = e, a = h / 4) : a = h / (2 * Math.PI) * Math.asin(e / g);
						return 1 > b ? -0.5 * g * Math.pow(2, 10 * (b -= 1)) * Math.sin((b * d - a) * 2 * Math.PI / h) + c : 0.5 * g *
							Math.pow(2, -10 * (b -= 1)) * Math.sin((b * d - a) * 2 * Math.PI / h) + e + c
					},
					easeOutElastic: function(a, b, c, e, d) {
						var a = 1.70158,
							h = 0,
							g = e;
						if (0 == b) return c;
						if (1 == (b /= d)) return c + e;
						h || (h = 0.3 * d);
						g < Math.abs(e) ? (g = e, a = h / 4) : a = h / (2 * Math.PI) * Math.asin(e / g);
						return g * Math.pow(2, -10 * b) * Math.sin((b * d - a) * 2 * Math.PI / h) + e + c
					},
					easeInBounce: function(a,
						b, c, e, f) {
						return e - d.D.easing.easeOutBounce(a, f - b, 0, e, f) + c
					},
					easeOutBounce: function(a, b, c, e, d) {
						return (b /= d) < 1 / 2.75 ? e * 7.5625 * b * b + c : b < 2 / 2.75 ? e * (7.5625 * (b -= 1.5 / 2.75) * b +
							0.75) + c : b < 2.5 / 2.75 ? e * (7.5625 * (b -= 2.25 / 2.75) * b + 0.9375) + c : e * (7.5625 * (b -= 2.625 /
							2.75) * b + 0.984375) + c
					},
					easeInOutBounce: function(a, b, c, e, f) {
						return b < f / 2 ? 0.5 * d.D.easing.easeInBounce(a, 2 * b, 0, e, f) + c : 0.5 * d.D.easing.easeOutBounce(a, 2 *
							b - f, 0, e, f) + 0.5 * e + c
					}
				},
				queue: {
					queue: [],
					add: function(a) {
						for (var b = this.queue, c = 0; c < b.length; c++)
							if (b[c] == a) return !1;
						b.push(a);
						return !0
					},
					remove: function(a) {
						for (var b = this.queue, c = [], e = 0; e < b.length; e++) b[e] != a && c.push(b[e]);
						this.queue = c
					}
				},
				timers: [],
				timerId: null,
				animExt: function(a, b, c) {
					this.options = b;
					this.elem = a;
					this.prop = c
				},
				htmlToElement: function(a) {
					var b;
					d.B.IE ? (b = new ActiveXObject("MSXml.DOMDocument"), b.loadXML(a)) : (b = document.createElement("DIV"), b.innerHTML =
						a);
					a = b.childNodes;
					b = 0;
					for (var c = a.length; b < c; b++);
					return a
				},
				elementToObject: function(a) {
					var b = {},
						c = d.isArray(a) ? a[0] : a;
					if (!c) return b;
					b[c.tagName.toLowerCase()] = c.innerHTML;
					if (c.attributes)
						for (var e = 0; e < c.attributes.length; e++)(a = c.attributes[e].name) && (b[a] = c.attributes[e].value);
					else b.msg = c.textContent;
					return b
				},
				jsonToxml: function(a) {
					var b = "",
						c;
					if ("object" == typeof a)
						for (var e in a) {
							if ("function" != typeof a[e])
								if ("string" == typeof a[e] && "text" == e) b = a[e];
								else if (d.isArray(a)) b += this.jsonToxml(a[e]);
							else {
								b += "<" + e;
								if ("object" == typeof a[e].attributes) {
									for (var f in a[e].attributes) "function" != typeof a[e].attributes[f] && (b += " " + f + '="' + a[e].attributes[
										f] + '"');
									delete a[e].attributes
								}
								c =
									this.jsonToxml(a[e]);
								b = a[e] && c ? b + (">" + c + "</" + e + ">") : b + ("></" + e + ">")
							}
						} else return a;
					return b
				}
			},
			E: function() {
				function a(a) {
					return a === l ? "theWindow" : a === document ? "theDocument" : a.uniqueID
				}

				function b(a) {
					return function(b) {
						var e = b.relatedTarget,
							c;
						if (!(c = this == e))
							if (this == e) c = !1;
							else {
								for (; e && e != this;) e = e.parentNode;
								c = e == this
							} c || a.call(this, b)
					}
				}

				function c() {
					this.cancelBubble = !0
				}

				function e() {
					this.returnValue = !1
				}
				var f, h, g = [];
				l.addEventListener ? (f = function(a, e, c, d) {
					"mouseenter" == e ? a.addEventListener("mouseover",
						b(c), d) : "mouseleave" == e ? a.addEventListener("mouseout", b(c), d) : a.addEventListener(e, c, d)
				}, h = function(a, b, e, c) {
					a.removeEventListener(b, e, c)
				}) : (f = function(b, e, c) {
					var d = "{FNKEY::obj_" + a(b) + "::evt_" + e + "::fn_" + c + "}",
						f = g[d];
					"undefined" == typeof f && (f = function(a) {
						c.call(b, a)
					}, g[d] = f, b.attachEvent("on" + e, f), l.attachEvent("onunload", function() {
						try {
							b.detachEvent("on" + e, f)
						} catch (a) {}
					}), d = null)
				}, h = function(b, e, c) {
					var c = "{FNKEY::obj_" + a(b) + "::evt_" + e + "::fn_" + c + "}",
						d = g[c];
					"undefined" != typeof d && (b.detachEvent("on" +
						e, d), delete g[c])
				});
				return {
					__evtHash: g,
					addEvent: f,
					removeEvent: h,
					fixEvent: function(a) {
						if (a && a.target) return a;
						a = a || l.event;
						a.pageX = a.clientX + d.D.scrollLeft();
						a.pageY = a.clientY + d.D.scrollTop();
						a.target = a.srcElement;
						a.stopPropagation = c;
						a.preventDefault = e;
						switch (a.type) {
							case "mouseout":
								a.relatedTarget = a.toElement;
								break;
							case "mouseover":
								a.relatedTarget = a.fromElement
						}
						return a
					},
					fireEvent: function(a, b) {
						if (d.B.IE) try {
							a.fireEvent("on" + b)
						} catch (e) {
							return !1
						} else {
							var c = document.createEvent("HTMLEvents");
							c.initEvent(b,
								!0, !0);
							try {
								a && a.dispatchEvent(c)
							} catch (f) {}
						}
						return !0
					}
				}
			}(),
			sfn: {},
			S: {
				camelize: function(a) {
					return a.replace(/-([a-z])/ig, function(a, c) {
						return c.toUpperCase()
					})
				},
				toArray: function(a) {
					if (!a || "null" == a) return "";
					for (var b = {}, c = [], e = [], c = a.replace(/https:/ig, "{https}").replace(/http:/ig, "{http}").replace(
							/;/ig, ",").split(","), a = c.length, d = 0; d < a; d++) c[d] && (e = c[d].split(":"), b[e[0]] = e[1].replace(
						/(^\s*)/g, "").replace(/(\s*$)/g, "").replace(/{http}/ig, "http:").replace(/{https}/ig, "https:"));
					return b
				},
				len: function(a) {
					if (!a) return 0;
					for (var b = 0, c = a.length, e = 0; e < c; e++) b = 255 < a.charCodeAt(e) ? b + 2 : b + 1;
					return b
				},
				cut: function(a, b, c) {
					if (!a) return "";
					var e = 0,
						b = 2 * b,
						f = a.length,
						h = "";
					if (d.S.len(a) <= b) return a;
					for (var g = 0; g < f; g++)
						if (e = 255 < a.charCodeAt(g) ? e + 2 : e + 1, e == b || c && e == b - 2) {
							h = a.substring(0, g + 1) + (c ? ".." : "");
							break
						} else if (e > b || c && e > b - 2) {
						h = a.substring(0, g) + "" + (c ? ".." : "");
						break
					}
					return h
				},
				_replace: function(a, b) {
					for (var c in a) "string" == typeof a[c] ? a[c] = !0 === b ? a[c].replace(/\"/ig, "{sy}") : a[c].replace(
							/\{sy\}/ig, '"') : "function" != typeof a[c] &&
						"object" == typeof a[c] && this._replace(a[c], b);
					return a
				},
				protocolFilter: function(a, b) {
					if ("object" == typeof a) {
						for (var c in a) a[c] = this.protocolFilter(a[c], b);
						return a
					}
					if ("undefined" !== typeof a) return a.toString().replace(/^https?:/ig, b || d.protocol)
				},
				decToHex: function(a) {
					for (var b = [], c = 0; c < a.length; c++) b[c] = /[\u4e00-\u9fa5]/i.test(a.charAt(c)) ? "\\u" + ("00" + a.charCodeAt(
						c).toString(16)).slice(-4) : a.charAt(c);
					return b.join("")
				},
				hexToDec: function(a) {
					if (!a) return "";
					var b = a.match(/\\u([0-9a-zA-Z]{2,4})|&#(\d+);/gi);
					if (b)
						for (var c = 0; c <= b.length; c++)
							if (b[c]) {
								var e, d = !1;
								e = b[c].replace("\\u", "");
								for (var h = 0; h < e.length; h++) - 1 == "0123456789abcdef".indexOf(e.charAt(h)) && (d = !0);
								d || (e = String.fromCharCode(parseInt(e, 16)), a = a.replace(b[c], e))
							} return a
				},
				secondsToMinutes: function(a) {
					var b = "",
						c = function(a, b) {
							var c = "00" + a;
							return c.substr(c.length - (b || 2))
						};
					60 <= a ? (b += c(parseInt(a / 60)), a %= 60) : b += "00";
					return b + ":" + c(a)
				},
				formatNumber: function(a, b) {
					for (var c = "", e = 1, d = b || 2; e <= d; e++) c += "0";
					c += a;
					return c.substr(c.length - d)
				},
				formatDate: function(a,
					b) {
					var c, e = a ? new Date(a) : new Date,
						d = this.formatNumber,
						h = b ? b : "h:i:s MS";
					c = "" + (-1 < h.indexOf("Y") ? d(e.getFullYear(), 4) + h.substr(h.indexOf("Y") + 1, 1) : "");
					c += -1 < h.indexOf("m") ? d(e.getMonth() + 1) + h.substr(h.indexOf("m") + 1, 1) : "";
					c += -1 < h.indexOf("d") ? d(e.getDate()) + h.substr(h.indexOf("d") + 1, 1) : "";
					c += -1 < h.indexOf("h") ? d(e.getHours()) + h.substr(h.indexOf("h") + 1, 1) : "";
					c += -1 < h.indexOf("i") ? d(e.getMinutes()) + h.substr(h.indexOf("i") + 1, 1) : "";
					c += -1 < h.indexOf("s") ? d(e.getSeconds()) + h.substr(h.indexOf("s") + 1, 1) : "";
					return c +=
						-1 < h.indexOf("MS") ? d(e.getMilliseconds(), 3) + h.substr(h.indexOf("MS") + 2, 1) : ""
				}
			},
			J: function() {
				function a(a) {
					g.lastIndex = 0;
					return g.test(a) ? '"' + a.replace(g, function(a) {
						var b = i[a];
						return "string" === typeof b ? b : "\\u" + ("0000" + a.charCodeAt(0).toString(16)).slice(-4)
					}) + '"' : '"' + a + '"'
				}

				function b(d, h) {
					var g, i, j, m, l, p = c,
						n = h[d];
					"function" === typeof f && (n = f.call(h, d, n));
					switch (typeof n) {
						case "string":
							return a(n);
						case "number":
							return isFinite(n) ? String(n) : "null";
						case "boolean":
						case "null":
							return String(n);
						case "object":
							if (!n) return "null";
							c += e;
							l = [];
							if ("[object Array]" === Object.prototype.toString.apply(n)) {
								m = n.length;
								for (g = 0; g < m; g += 1) l[g] = b(g, n) || "null";
								j = 0 === l.length ? "[]" : c ? "[\n" + c + l.join(",\n" + c) + "\n" + p + "]" : "[" + l.join(",") + "]";
								c = p;
								return j
							}
							if (f && "object" === typeof f) {
								m = f.length;
								for (g = 0; g < m; g += 1) i = f[g], "string" === typeof i && (j = b(i, n)) && l.push(a(i) + (c ? ": " :
									":") + j)
							} else
								for (i in n) Object.hasOwnProperty.call(n, i) && (j = b(i, n)) && l.push(a(i) + (c ? ": " : ":") + j);
							j = 0 === l.length ? "{}" : c ? "{\n" + c + l.join(",\n" + c) + "\n" + p + "}" : "{" + l.join(",") + "}";
							c = p;
							return j
					}
				}
				var c, e, f, h =
					/[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
					g =
					/[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
					i = {
						"\b": "\\b",
						"\t": "\\t",
						"\n": "\\n",
						"\f": "\\f",
						"\r": "\\r",
						'"': '\\"',
						"\\": "\\\\"
					};
				return {
					toJSONString: function(a, e, c) {
						var d;
						if ("number" === typeof c)
							for (d = 0; d < c; d += 1);
						if ((f = e) && "function" !== typeof e && ("object" !== typeof e || "number" !== typeof e.length)) throw Error(
							"JSON.toString");
						return b("", {
							"": a
						})
					},
					parseJSON: function(a, b) {
						function e(a, c) {
							var d, f, h = a[c];
							if (h && "object" === typeof h)
								for (d in h) Object.hasOwnProperty.call(h, d) && (f = e(h, d), void 0 !== f ? h[d] = f : delete h[d]);
							return b.call(a, c, h)
						}
						var c;
						if (a = String(a).replace(/\r|\n/ig, "")) try {
							a = d.S.hexToDec(a)
						} catch (f) {}
						h.lastIndex = 0;
						h.test(a) && (a = a.replace(h, function(a) {
							return "\\u" + ("0000" + a.charCodeAt(0).toString(16)).slice(-4)
						}));
						if (/^[\],:{}\s]*$/.test(a.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, "@").replace(
								/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,
								"]").replace(/(?:^|:|,)(?:\s*\[)+/g, ""))) return c = eval("(" + a + ")"), "function" === typeof b ? e({
							"": c
						}, "") : c;
						throw new SyntaxError("JSON.toJSON");
					}
				}
			}(),
			H: function() {
				this.__hash = {};
				this.__index = [];
				var a = this;
				return {
					__hash: this.__hash,
					__index: this.__index,
					add: function(b, c) {
						if ("undefined" != typeof b) {
							if (this.contains(b)) return !1;
							a.__hash[b] = "undefined" == typeof c ? null : c;
							a.__index.push(b);
							return !0
						}
					},
					remove: function(b) {
						var c = this.next(b),
							e = this.index(b);
						delete a.__hash[b];
						!1 !== e && delete a.__index[e];
						return a.__hash[c]
					},
					first: function() {
						for (var a in this.__hash)
							if ("function" != typeof this.__hash[a]) return this.__hash[a]
					},
					previous: function(b) {
						var c, e;
						for (e in a.__hash)
							if ("function" != typeof a.__hash[e]) {
								if (e == b && c) break;
								c = e
							} return c
					},
					next: function(b) {
						var c = null,
							e = !1,
							d;
						for (d in a.__hash)
							if ("function" != typeof a.__hash[d])
								if (c || (c = d), d == b) e = !0;
								else if (e) return d;
						return c
					},
					count: function() {
						var b = 0,
							c;
						for (c in a.__hash) "function" != typeof a.__hash[c] && b++;
						return b
					},
					items: function(b) {
						return a.__hash[b]
					},
					index: function(b) {
						for (var c =
								0; c < a.__index.length; c++)
							if (a.__index[c] == b) return c;
						return !1
					},
					contains: function(b) {
						return "undefined" != typeof a.__hash[b]
					},
					clear: function() {
						for (var b in a.__hash) "function" != typeof a.__hash[b] && delete a.__hash[b];
						a.index = []
					},
					each: function(b) {
						for (var c in a.__hash) "function" != typeof a.__hash[c] && b.call(a, c, a.__hash[c])
					}
				}
			},
			Base64: {
				_strKey: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",
				encode: function(a) {
					for (var b = d.Base64, c = "", e, f, h, g, i, k, j = 0, a = b._utf8_encode(a || ""); j < a.length;) e =
						a.charCodeAt(j++), f = a.charCodeAt(j++), h = a.charCodeAt(j++), g = e >> 2, e = (e & 3) << 4 | f >> 4, i = (
							f & 15) << 2 | h >> 6, k = h & 63, isNaN(f) ? i = k = 64 : isNaN(h) && (k = 64), c = c + b._strKey.charAt(g) +
						b._strKey.charAt(e) + b._strKey.charAt(i) + b._strKey.charAt(k);
					return c
				},
				decode: function(a) {
					for (var b = d.Base64, c = "", e, f, h, g, i, k = 0, a = (a || "").replace(/[^A-Za-z0-9\+\/\=]/g, ""); k < a.length;)
						e = b._strKey.indexOf(a.charAt(k++)), f = b._strKey.indexOf(a.charAt(k++)), g = b._strKey.indexOf(a.charAt(k++)),
						i = b._strKey.indexOf(a.charAt(k++)), e = e << 2 | f >> 4, f = (f &
							15) << 4 | g >> 2, h = (g & 3) << 6 | i, c += String.fromCharCode(e), 64 != g && (c += String.fromCharCode(f)),
						64 != i && (c += String.fromCharCode(h));
					return c = b._utf8_decode(c)
				},
				_utf8_encode: function(a) {
					for (var a = a.replace(/\r\n/g, "\n"), b = "", c = 0; c < a.length; c++) {
						var e = a.charCodeAt(c);
						128 > e ? b += String.fromCharCode(e) : (127 < e && 2048 > e ? b += String.fromCharCode(e >> 6 | 192) : (b +=
							String.fromCharCode(e >> 12 | 224), b += String.fromCharCode(e >> 6 & 63 | 128)), b += String.fromCharCode(
							e & 63 | 128))
					}
					return b
				},
				_utf8_decode: function(a) {
					for (var b = "", c = 0, e = c1 = c2 =
							0; c < a.length;) e = a.charCodeAt(c), 128 > e ? (b += String.fromCharCode(e), c++) : 191 < e && 224 > e ? (
						c2 = a.charCodeAt(c + 1), b += String.fromCharCode((e & 31) << 6 | c2 & 63), c += 2) : (c2 = a.charCodeAt(c +
						1), c3 = a.charCodeAt(c + 2), b += String.fromCharCode((e & 15) << 12 | (c2 & 63) << 6 | c3 & 63), c += 3);
					return b
				}
			},
			utils: {
				options: {},
				handleLinks: function(a, b) {
					this.options = n.extend({}, this.options, b);
					for (var c = 0; c < this.linkPatterns.length; c++) a = a.replace(this.linkPatterns[c][0], this.linkPatterns[c]
						[1]);
					return a
				},
				linkPatterns: [
					[/\[link\s+reconnect=([^\s\[\]\'\"]+)\s*[^\[\]]*\]([^\[\]]+)\[\/\s*link\]/gi,
						'<a style="color:#00f;text-decoration:none;" href="javascript:void(0);" onclick="NTKF.chatManage.get(\'$1\').reconnect(this);return false;" >$2</a>'
					],
					[/\[link\s+message=([^\s\[\]\'\"]+)\s*[^\[\]]\s*source=([^\s\[\]\'\"]+)\s*[^\[\]]*\]([^\[\]]+)\[\/\s*link\]/gi,
						'<a style="color:#00f;text-decoration:none;" href="javascript:void(0);" onclick="NTKF.chatManage.get(\'$1\').switchUI(1, $2);return false;" >$3</a>'
					],
					[/\[link\s+cancel=([^\s\[\]\'\"]+)\s*[^\[\]]*\]([^\[\]]+)\[\/\s*link\]/gi,
						'<a style="color:#00f;text-decoration:none;" href="javascript:void(0);" onclick="NTKF.chatManage.get(\'$1\').cancelUpload(1, 5);return false;" >$2</a>'
					],
					[/\[link\s*(.*?)?\](.*?)\[\/link\]/gi,
						'<a style="color:#00f;text-decoration:none;" href="$1" target="_blank">$2</a>'
					],
					[/\{\$(\w+)\}/gi, function(a, b) {
						return d.utils.options[b] || "<\!--" + b + "--\>"
					}]
				]
			}
		});
		var t = d.$,
			n = d.A,
			s = d.B,
			B = d.E,
			p = d.J;
		d.extend({
			DOMContentLoaded: !1,
			DOMLoadedFn: [],
			ready: function(a) {
				var b = this,
					c = "webkit" == d.B.CORE && 525 > d.B.Version,
					e = function() {
						b.DOMContentLoaded || (b.DOMContentLoaded = !0, !c && !d.B.IE && (window.removeEventListener("load", e, !1),
							document.removeEventListener("DOMContentLoaded",
								e, !1)), f.call())
					},
					f = function() {
						for (var a = 0; a < b.DOMLoadedFn.length; a++) b.DOMLoadedFn[a].apply(document);
						b.DOMLoadedFn = []
					};
				b.DOMContentLoaded || /loaded|complete/.test(document.readyState) ? (b.DOMContentLoaded = !0, a.call(b)) : b.DOMLoadedFn[
					b.DOMLoadedFn.length] = function() {
					return a.call(b)
				};
				if (c) /^loaded|complete$/i.test(document.readyState) ? e() : setTimeout(function() {
					arguments.callee()
				}, 50);
				else if (d.B.IE) {
					var h = document.createElement("div");
					(function() {
						try {
							h.doScroll("left"), document.body.insertBefore(h,
								document.body.lastChild).setAttribute("html", "temp")
						} catch (a) {}
						return h
					}) ? e(): setTimeout(function() {
						arguments.callee()
					}, 50)
				} else window.addEventListener("load", e), document.addEventListener("DOMContentLoaded", e)
			}
		});
		d.D.animExt.prototype = {
			update: function() {
				switch (this.prop.toLowerCase()) {
					case "scrollleft":
						this.elem.scrollLeft = this.now;
						break;
					case "scrolltop":
						this.elem.scrollTop = this.now;
						break;
					case "opacity":
						d.D.css(this.elem, "opacity", this.now / 100);
						break;
					default:
						this.elem.style[this.prop] = this.now + this.unit
				}
			},
			custom: function(a, b, c) {
				function e(a) {
					return f.step(a)
				}
				this.startTime = +new Date;
				this.start = a;
				this.end = b;
				this.unit = c || "px";
				this.now = this.start;
				this.pos = this.state = 0;
				this.update();
				var f = this;
				e.elem = this.elem;
				e() && (d.D.timers.push(e) && null == d.D.timerId) && (d.D.timerId = setInterval(function() {
					for (var a = d.D.timers, b = 0; b < a.length; b++) a[b]() || a.splice(b, 1);
					a.length || (clearInterval(d.D.timerId), d.D.timerId = null)
				}, 13))
			},
			step: function(a) {
				var b = +new Date;
				if (a || b > this.options.duration + this.startTime) {
					this.now = this.end;
					this.pos = this.state = 1;
					this.update();
					var a = this.options.curAnim[this.prop] = !0,
						c;
					for (c in this.options.curAnim) !0 !== this.options.curAnim[c] && (a = !1);
					d.D.queue.remove(this.elem);
					a && "function" == typeof this.options.complete && this.options.complete.apply(this, [this.elem]);
					return !1
				}
				c = b - this.startTime;
				this.state = c / this.options.duration;
				a = this.options.easing || (d.D.easing.swing ? "swing" : "linear");
				this.pos = d.D.easing[this.options.specialEasing && this.options.specialEasing[this.prop] || a](this.state, c, 0,
					1, this.options.duration);
				this.now = this.start + (this.end - this.start) * this.pos;
				this.update();
				return !0
			}
		};
		d.extend({
			url: y.href,
			title: document.title,
			url_referrer: document.referrer,
			url_source: "",
			charset: document.characterSet || document.charset,
			lang: x.language || x.systemLanguage,
			params: function() {
				for (var a = {
						_hash: y.hash,
						search: y.search
					}, b = y.search.replace("?", "").split("&"), c = 0; c < b.length; c++)
					if (b[c] && -1 < b[c].indexOf("=")) {
						var e = b[c].indexOf("=");
						try {
							a[b[c].substr(0, e)] = decodeURIComponent(b[c].substr(e + 1, b[c].length))
						} catch (d) {
							a[b[c].substr(0,
								e)] = b[c].substr(e + 1, b[c].length)
						}
					} return a
			}(),
			domain: function() {
				var a =
					/([^\.]+(\.com(\.cn)?|\.net(\.cn)?|\.org(\.cn)?|\.gov(\.cn)?|\.cn|\.mobi|\.tel|\.asia|\.me|\.info|\.hk|\.cc|\.biz|\.tv))$/i
					.exec(document.domain);
				return a && a[0] || document.domain
			}()
		});
		d.extend({
			Class: {
				create: function() {
					return function() {
						this.initialize.apply(this, arguments)
					}
				}
			},
			require: function(a, b, c, e) {
				b = d.url_resource + "/" + (b || "ntchat.js?version=" + d.version_ntchat);
				"undefined" == typeof c ? d.C.load(b, function(b) {
					"complete" != b.status ?
						d.debug.Log(b.uri + ", status:" + b.status, 2) : setTimeout(function() {
							a()
						}, 50)
				}, e, {
					verification: !0,
					remove: !1
				}) : a()
			},
			createTimeID: function(a) {
				var b = parseInt((new Date).getTime()),
					c;
				c = "000" + parseInt(Math.floor(1E3 * Math.random()));
				c = c.substr(c.length - 6);
				switch (a) {
					case 2:
						return b.toString() + c.toString();
					case 1:
						return b + c;
					default:
						return b
				}
			},
			randomChar: function(a) {
				for (var b = "", a = a || 8, c = 0; c < a; c++) b += "0123456789ABCDEF".charAt(Math.ceil(1E8 * Math.random()) %
					16);
				return b
			}
		});
		d.lockPosition = d.Class.create();
		d.lockPosition.prototype = {
			element: null,
			options: null,
			offset: null,
			rect: null,
			nfixTimeID: null,
			objectDrag: null,
			initialize: function(a, b) {
				var c = this;
				this.element = d.isArray(a) ? a : t(a);
				this.offset = d.bodyOffset;
				this.rect = this.element.rect();
				this.options = n.extend({
					debug: !1,
					fixed: !1,
					drag: !1,
					origin: !1,
					handler: this.element,
					resize: !0,
					jsScroll: !1,
					onChange: new Function,
					left: Math.max(0, this.rect.left) || 0,
					top: Math.max(0, this.rect.top) || 0,
					width: Math.max(0, this.rect.width),
					height: Math.max(0, this.rect.height)
				}, b);
				this.options = n.extend(this.options, {
					right: this.offset.width - this.options.left - this.options.width,
					bottom: this.offset.viewH - this.options.top - this.options.height
				});
				if (this.element && this.element.length) {
					this.options.debug && d.debug.Log("NTKF.lockPosition.initialize[" + this.element.attr("id") + "]: attrs:" + p.toJSONString(
						n.get(this.options, ["left", "top", "right", "bottom"])), 1);
					if (this.options.fixed) {
						var e = this.element.css("position");
						s.IE6 || s.OLDIE && !s.CSS1Compat ? "absolute" != e && this.element.css("position", "absolute") : "fixed" != e &&
							this.element.css("position",
								"fixed");
						this.fixedPosition()
					} else this.element.css({
						position: "absolute",
						left: this.options.left + "px",
						top: this.options.top + "px"
					}), this.nfixTimeID = setInterval(function() {
						c.animationPosition()
					}, 100);
					this.options.drag && (e = n.wget({
						startDrag: function() {
							c.startDrag()
						},
						moving: function(a) {
							c.moving(a)
						},
						stopDrag: function(a) {
							c.stopDrag(a)
						},
						resize: !1,
						position: !1
					}, this.options, "debug origin handler left top right bottom".split(" ")), this.objectDrag = new d.Drag(this.element,
						e));
					this.options.resize && d.E.addEvent(l,
						"resize",
						function() {
							c.onResize()
						})
				}
			},
			stopAnimation: function() {
				this.options.debug && d.debug.Log("stopAnimation", 1);
				clearInterval(this.nfixTimeID);
				this.nfixTimeID = null
			},
			animationPosition: function() {
				this.rect = this.element.rect();
				this.offset = d.bodyOffset;
				var a = {
						form: {
							offsetLeft: this.offset.viewX + parseInt(this.rect.left),
							offsetTop: this.offset.viewY + parseInt(this.rect.top)
						},
						to: {
							offsetLeft: this.offset.viewX + (this.options.left || 0),
							offsetTop: this.offset.viewY + (this.options.top || 0)
						}
					},
					a = n.extend(a, {
						set: {
							offsetLeft: a.form.offsetLeft +
								(a.to.offsetLeft - a.form.offsetLeft) / 3.9,
							offsetTop: a.form.offsetTop + (a.to.offsetTop - a.form.offsetTop) / 3.9
						}
					});
				a.form.offsetTop != a.to.offsetTop && (3.9 > Math.abs(a.form.offsetTop - a.to.offsetTop) ? this.element.css({
					left: a.to.offsetLeft + "px",
					top: a.to.offsetTop + "px"
				}) : (this.options.debug && d.debug.Log("animationPosition: form:" + p.toJSONString(a.form) + ", to:" + p.toJSONString(
					a.to) + ", set:" + p.toJSONString(a.set)), this.element.css({
					left: a.set.offsetLeft + "px",
					top: a.set.offsetTop + "px"
				})))
			},
			scrollPosition: function() {
				var a = {
					offsetLeft: this.offset.left + this.options.left,
					offsetTop: this.offset.viewY + this.options.top
				};
				this.options.debug && d.debug.Log("scrollPosition:" + p.toJSONString(a));
				this.element.css({
					left: a.offsetLeft + "px",
					top: a.offsetTop + "px"
				})
			},
			fixedPosition: function() {
				var a = this,
					b = function() {
						a.scrollPosition()
					};
				this.options.left = Math.min(Math.max(0, this.options.left), this.offset.width - this.options.width);
				this.options.top = Math.min(Math.max(0, this.options.top), this.offset.viewH - this.options.height);
				if (s.IE6 && this.options.jsScroll) setTimeout(b,
					0), d.E.addEvent(l, "resize", b), d.E.addEvent(l, "scroll", b);
				else if (s.IE6 || s.OLDIE && !s.CSS1Compat) {
					var c = d.D.scrollTop();
					this.options = n.extend(this.options, {
						offsetLeft: this.offset.viewX + this.options.left,
						offsetTop: this.offset.viewY + this.options.top,
						offsetRight: this.offset.width - this.options.left - this.options.width,
						offsetBottom: this.offset.viewH - this.options.top - this.options.height
					});
					this.options.debug && d.debug.Log("set position:" + p.toJSONString(n.get(this.options, ["offsetLeft",
						"offsetTop", "offsetRight",
						"offsetBottom"
					])), 1);
					setTimeout(function() {
						a.element.first().style.cssText += ";left:" + a.options.offsetLeft + "px;top:" + a.options.offsetTop + "px;";
						a.element.first().style.cssText += ";left:" + a.options.offsetLeft +
							"px;top:expression(eval((document.documentElement.scrollTop || document.body.scrollTop) + (document.documentElement.clientHeight||document.body.clientHeight) - this.offsetHeight - " +
							a.options.offsetBottom + "));";
						d.D.scrollTop(document, c + 1)
					}, 0)
				} else this.element.css({
					left: this.options.left + "px",
					top: this.options.top +
						"px"
				})
			},
			onResize: function() {
				this.updateAttributes()
			},
			updateAttributes: function(a) {
				var b = {
					left: ["right", "width", "width"],
					top: ["bottom", "viewH", "height"],
					right: ["left", "width", "width"],
					bottom: ["top", "viewH", "height"]
				};
				this.offset = d.bodyOffset = t(document.body).rect();
				if (a) {
					this.options = n.extend(this.options, a);
					for (var c in a) b[c] && "function" != typeof b[c] && (this.options[b[c][0]] = this.offset[b[c][1]] - this.options[
						c] - this.options[b[c][2]])
				} else this.options.left = this.offset.width - this.options.right - this.options.width,
					this.options.top = this.offset.viewH - this.options.bottom - this.options.height;
				this.objectDrag && this.objectDrag.onResize();
				this.options.debug && d.debug.Log("updateAttributes:[" + this.element.attr("id") + "]" + p.toJSONString(n.get(
					this.options, ["left", "top", "right", "bottom"])));
				this.options.fixed && this.fixedPosition()
			},
			onChange: function() {
				this.options.onChange.call(this, {
					left: this.offset.viewX + this.options.left,
					top: this.offset.viewY + this.options.top
				})
			},
			clearAutoUpdatePosition: function() {
				this.startDrag()
			},
			startDrag: function() {
				var a =
					this,
					b = function() {
						a.scrollPosition()
					};
				this.options.fixed || this.stopAnimation();
				d.E.removeEvent(l, "resize", b);
				d.E.removeEvent(l, "scroll", b)
			},
			moving: function(a) {
				this.options.debug && d.debug.Log("moving:" + p.toJSONString(a), 1);
				this.options = n.extend(this.options, a);
				this.onChange()
			},
			stopDrag: function(a) {
				var b = this;
				this.options.debug && d.debug.Log("stopDrag(" + p.toJSONString(a) + ")");
				this.options = n.extend(this.options, a);
				this.options.fixed ? this.fixedPosition() : this.nfixTimeID = setInterval(function() {
						b.animationPosition()
					},
					50);
				this.options.onDrop && a && this.options.onDrop(a)
			}
		};
		d.Drag = d.Class.create();
		d.Drag.prototype = {
			element: null,
			handlerElement: null,
			originElement: null,
			options: null,
			offset: null,
			rect: null,
			dragArea: null,
			offsetX: 0,
			offsetY: 0,
			dragMove: !1,
			initialize: function(a, b) {
				var c = this;
				this.element = d.isArray(a) ? a : t(a);
				this.offset = d.bodyOffset;
				this.rect = this.element.rect();
				if (this.element.length) {
					this.options = n.extend({
						debug: !1,
						startDrag: new Function,
						moving: new Function,
						stopDrag: new Function,
						handler: this.element,
						origin: !1,
						resize: !0,
						position: !0,
						left: this.rect.left,
						top: this.rect.top,
						width: this.rect.width,
						height: this.rect.height,
						zIndex: parseInt(this.element.css("zIndex")) || 1
					}, b);
					this.dragArea = this.options.area;
					this.onResize();
					this.handlerElement = t(this.options.handler);
					this.options.resize && B.addEvent(l, "resize", function() {
						c.onResize()
					});
					if (this.options.position) {
						var e = this.element.css("position");
						s.IE6 || s.OLDIE && !s.CSS1Compat ? "absolute" != e && this.element.css("position", "absolute") : "fixed" != e &&
							this.element.css("position",
								"fixed")
					}
					this.element.css({
						left: this.options.left + "px",
						top: this.options.top + "px",
						right: "auto",
						bottom: "auto"
					});
					this.handlerElement.bind("mousedown", function(a) {
						a = d.E.fixEvent(a);
						c.onMousedown(a)
					})
				}
			},
			onMousedown: function(a) {
				var b = this;
				if (!(d.B.OLDIE && 1 != a.button || !d.B.OLDIE && 0 != a.button)) {
					this.options.origin && (this.originElement = t(this.element.first().cloneNode(!0)), this.originElement.css({
						zIndex: 1
					}), this.element.first().parentNode.appendChild(this.originElement.first()));
					this.offset = t(document.body).rect();
					this.options = n.extend(this.options, {
						X: this.offset.viewX,
						Y: this.offset.viewY
					});
					this.dragMove = !0;
					this.handlerElement.css({
						cursor: "move"
					}).first().style.cursor = "move";
					this.element.css({
						opacity: 0.5,
						zIndex: 2147483647
					});
					this.offsetX = a.pageX - this.offset.viewX - this.options.left;
					this.offsetY = a.pageY - this.offset.viewY - this.options.top;
					if (d.B.OLDIE) this.element.first().setCapture();
					else try {
						window.captureEvents(Event.MOUSEMOVE | Event.MOUSEUP)
					} catch (c) {}
					d.B.MOZ && (a.preventDefault(), a.stopPropagation());
					this.options.debug &&
						d.debug.Log("onMousedown: " + p.toJSONString(n.get(this.options, ["X", "Y", "left", "top"])));
					this.options.startDrag(n.get(this.options, ["X", "Y", "left", "top"]));
					document.onmousemove = function(a) {
						a = d.E.fixEvent(a);
						b.onMouseMove(a)
					};
					document.onmouseup = function(a) {
						a = d.E.fixEvent(a);
						b.onMouseup(a)
					}
				}
			},
			onMouseMove: function(a) {
				d.B.OLDIE && (a.returnValue = !1);
				document.selection ? document.selection.empty() : window.getSelection().removeAllRanges();
				if (this.dragMove) {
					this.options.left = a.clientX - this.offsetX;
					this.options.top =
						a.clientY - this.offsetY;
					this.options.offsetLeft = this.options.left = Math.min(Math.max(this.dragArea.minLeft, this.options.left), this
						.dragArea.maxLeft);
					this.options.offsetTop = this.options.top = Math.min(Math.max(this.dragArea.minTop, this.options.top), this.dragArea
						.maxTop);
					if ("fixed" != this.element.css("position") && s.CSS1Compat || s.IE6) this.options.offsetLeft += this.options.X,
						this.options.offsetTop += this.options.Y;
					this.element.css({
						right: "auto",
						bottom: "auto",
						left: this.options.offsetLeft + "px",
						top: this.options.offsetTop +
							"px"
					});
					this.options.right = this.offset.width - this.options.left - this.options.width;
					this.options.bottom = this.offset.viewH - this.options.top - this.options.height;
					this.options.moving(n.get(this.options, "X Y left top right bottom".split(" ")))
				}
			},
			onMouseup: function() {
				if (this.dragMove) {
					this.dragMove = !1;
					this.options.origin && this.originElement.remove();
					if (d.B.OLDIE) this.element.first().releaseCapture();
					else try {
						l.releaseEvents(Event.MOUSEMOVE | Event.MOUSEUP)
					} catch (a) {}
					this.handlerElement.css("cursor", "default");
					this.element.css({
						opacity: 1,
						zIndex: this.options.zIndex
					});
					document.onmousemove = null;
					document.onmouseup = null;
					this.options.debug && d.debug.Log("onMouseup: " + p.toJSONString(n.get(this.options, ["left", "top"])));
					this.options.stopDrag(n.get(this.options, ["left", "top", "right", "bottom"]))
				}
			},
			onResize: function() {
				this.offset = t(document.body).rect();
				this.dragArea = n.extend(this.dragArea, {
					minLeft: 0,
					minTop: 0,
					maxLeft: 0 + this.offset.width - this.options.width - 2,
					maxTop: 0 + this.offset.viewH - this.options.height - 2
				});
				this.options.bottom ?
					(this.options.left = this.offset.width - this.options.right - this.options.width, this.options.top = this.offset
						.viewH - this.options.bottom - this.options.height) : (this.options.right = this.offset.width - this.options.left -
						this.options.width, this.options.bottom = this.offset.viewH - this.options.top - this.options.height);
				this.options.debug && d.debug.Log("onResize[" + this.element.attr("id") + "]:" + p.toJSONString(this.dragArea))
			}
		};
		d.extend({
			debug: {
				line: 0,
				element: null,
				enable: 0,
				level: 0,
				inited: !1,
				objScroll: null,
				cache: new d.H,
				CONST: [
					["DEBUG", "#64C864"],
					["INFO", "#000000"],
					["WARN", "#0000FF"],
					["ERROR", "#FF8C00"],
					["FATAL", "#FF0000"],
					["NONE", "#333333"]
				],
				_init: function() {
					this.inited || (this.inited = !0, this.enable = parseInt(d.params["ntalker-debug"] || d.CACHE.get("d.e") || 0),
						this.level = parseInt(d.params["ntalker-level"] || d.CACHE.get("d.l") || 0))
				},
				_create: function() {
					if (this.element) return this.element;
					if (document.body) {
						var a = this,
							b = d.$;
						cssText =
							"margin:0;padding:0;cursor:pointer;display:block;background:#eee;color:#333;widht:20px;height:20px;position:absolute;font:normal normal normal 12px/160% Arial,SimSun;";
						this.element = d.D.creElm({
							id: "ntkf_debug_container",
							style: "margin:0;padding:0;background:#fff;font:normal 12px/150% Arial,SimSun;position:absolute;left:0px;top:0px;height:320px;width:600px;z-index:1000009;border:1px solid #ccc;"
						}, "div");
						this.element.innerHTML =
							'<div id="ntkf_debug_header" style="margin:0;padding:0;border:0;text-align:right;color:#fff;font:normal 12px/150% Arial,SimSun;height:20px;background:#ccc;">&nbsp;</div><div id="ntkf_debug_button_hide" style="' +
							cssText + 'right:50px;top:0px;">Hide</div><div id="ntkf_debug_button_clear" style="' +
							cssText +
							'right:10px;top:0px;">Clear</div><div id="ntkf_debug_body" style="margin:0;padding:0;border:0;color:#00FF00;height:300px;width:100%;overflow-y:scroll;overflow-x:hidden;text-align:left;opacity:0.7;filter:alpha(opacity=70);font:normal 12px/160% Arial,SimSun;"></div><div id="ntkf_debug_footer"></div>';
						b("#ntkf_debug_button_hide, #ntkf_debug_button_clear").hover(function(a, e) {
							b(e).css("background-color", "#333").css("color", "#fff")
						}, function(a, e) {
							b(e).css("background-color", "#eee").css("color",
								"#333")
						}).bind("click", function(c, e) {
							"ntkf_debug_button_clear" != e.id ? (a.objScroll && a.objScroll.stopAnimation(), b(a.element).hide(!0, !0),
								d.CACHE.save("d.e", 0)) : b("#ntkf_debug_body").html("")
						});
						this.objScroll = new d.lockPosition(this.element, {
							handler: "#ntkf_debug_header",
							drag: !0,
							fixed: !1,
							onChange: function() {}
						})
					} else d.ready(function() {
						d.debug._create()
					})
				},
				open: function(a, b) {
					this.enable = a || 2;
					this.level = b || 0;
					d.CACHE.save("d.e", this.enable);
					d.CACHE.save("d.l", this.level);
					return d.CACHE.get("d")
				},
				Log: function(a,
					b) {
					this._init();
					var c, e = b || 0;
					if (this.enable && !(e < this.level)) {
						c = d.S.formatDate(0, "i:s MS ") + " - [" + this.CONST[e][0] + "] - ";
						c = "object" == typeof a ? a.message ? c + (a.message + " - " + a.fileName + "(" + (a.lineNumber ? a.lineNumber :
							a.number || "unknown") + ")") : c + d.J.toJSONString(a) : c + a;
						this.cache.add(d.S.formatDate(0, "d[h:i:s MS]"), c);
						if (2 == this.enable && l.console) return this._winLog(c, e);
						this._create();
						return this._createLog(c, e)
					}
				},
				cacheMark: {
					_lastKey: null
				},
				mark: function(a, b) {
					var c = a || d.randomChar();
					this.cacheMark[c] =
						d.createTimeID();
					b && this.cacheMark._lastKey && this.Log(this.cacheMark._lastKey + " To " + c + " Exec:" + (this.cacheMark[c] -
						this.cacheMark[this.cacheMark._lastKey]) + "ms", 5);
					return this.cacheMark._lastKey = c
				},
				useTime: function(a, b) {
					return !this.cacheMark[b] || !this.cacheMark[a] ? this.Log("Time point [" + a + "] or [" + b + "] not create",
						2) : this.Log(a + " To " + b + " Exec:" + (this.cacheMark[b] - this.cacheMark[a]) + "ms", 5)
				},
				_createLog: function(a, b) {
					var c = t("#ntkf_debug_body");
					if (!c.length) return a;
					t({
						style: "margin:0;padding:0;border:0;color:" +
							this.CONST[b][1] + ";font:normal normal normal 12px/160% Arial,SimSun;text-align:left;background-color:" +
							(0 == this.line % 2 ? "#eee" : "#fff") + ";"
					}, c, !1).html(a);
					c.last().scrollTop = c.last().scrollHeight;
					this.line++;
					return a
				},
				_winLog: function(a, b) {
					switch (this.CONST[b][0].toLowerCase()) {
						case "debug":
							l.console.debug ? l.console.debug(a) : l.console.info(a);
							break;
						case "info":
							l.console.info(a);
							break;
						case "warn":
							l.console.warn(a);
							break;
						case "fatal":
						case "error":
							l.console.warn(a);
							break;
						default:
							l.console.log(a)
					}
					return a
				},
				submitLog: function(a) {
					var b = this,
						c = [];
					if (0 != this.cache.count() && a) return this.cache.each(function(a, b) {
						c.push(b + "\n")
					}), new d.POST(a, {
						action: "savelog",
						data: c.join("")
					}, [function() {
						b.Log("Submit a log file successfully", 1)
					}, function() {
						b.Log("Failure to submit the log file", 3)
					}]), !0
				}
			}
		});
		d.extend({
			CACHE: function() {
				function a(a, e) {
					for (var f = a.split("."), h = b, g = null; g = f.shift();)
						if (f.length) !h[g] && (h[g] = {}), h = h[g];
						else {
							if (void 0 === e) return h[g] || null;
							if (d.isArray(e)) {
								var i = [];
								h[g] && (i = i.concat(h[g]));
								h[g] =
									i.concat(e)
							} else return h[g] = e, b
						}
				}
				var b = d.A.extend({
					uid: null,
					tid: null,
					fid: null
				}, d.J.parseJSON(d.Cookie.get("NTKF_CACHE_DATA") || "{}"));
				return {
					cache: b,
					get: function(c, e) {
						!0 == e && (b = d.A.extend(b, d.J.parseJSON(d.Cookie.get("NTKF_CACHE_DATA") || "{}")));
						return a(c)
					},
					save: function(c, e) {
						a(c, e);
						return d.Cookie.set("NTKF_CACHE_DATA", d.J.toJSONString(b), 0)
					},
					value: a
				}
			}()
		});
		d.extend({
			promptwindow: function() {
				function a() {
					f = !0;
					100 > d.createTimeID() - g || (g = d.createTimeID(), p && e(), d.isFunction(d.loadCacheMarketing) && d.loadCacheMarketing(),
						d.IMPRESENCE && d.IMPRESENCE.setPageFocus && (d.IMPRESENCE.setPageFocus(!0, d.title, d.url, 0), d.debug.Log(
							'setPageFocus(true, "' + d.title + '", "' + d.url + '", 1)', 0)))
				}

				function b() {
					f = !1;
					try {
						d.IMPRESENCE && d.IMPRESENCE.setPageFocus(!1)
					} catch (a) {}
				}

				function c() {
					n++;
					document.title = 0 == n % 2 ? j : k;
					try {
						top != self && (top.document.title = 0 == n % 2 ? j : k)
					} catch (a) {}
				}

				function e() {
					if (h && (clearInterval(i), i = null, n = 0, setTimeout(function() {
							document.title = m
						}, 500), h = !1, l.top != l.self)) try {
						l.top.document.title = m
					} catch (a) {}
				}
				var f = !1,
					h = !1,
					g =
					d.createTimeID(),
					i = null,
					k = null,
					j = null,
					m = document.title,
					n = 0,
					p = !0;
				d.B.IE && (d.E.addEvent(document, "onfocusin", a), d.E.addEvent(document, "onfocusout", b));
				d.E.addEvent(l, "focus", a);
				d.E.addEvent(l, "blur", b);
				return {
					originTitle: m,
					startPrompt: function(a, b, e) {
						if (!h && !f) {
							if (b && 0 < b.length) {
								k = d.String("\u3010%1\u3011%2 - %3").sprintf(b, "", m);
								j = "";
								a = 0;
								for (b = Math.ceil(d.S.len(b) / 2); a < b; a++) j += "\u3000";
								j = d.String("\u3010%1\u3011%2 - %3").sprintf(j, "", m)
							}
							p = e || p;
							i = setInterval(c, 800);
							return h = !0
						}
					},
					stopPrompt: e
				}
			}()
		});
		d.POST = d.Class.create();
		d.POST.prototype = {
			keyName: "",
			form: null,
			iframe: null,
			iframeName: "",
			url: "",
			data: {},
			_stopCall: !1,
			onComplete: null,
			onFailure: null,
			_loaded: !1,
			initialize: function(a, b, c, e) {
				this.keyName = d.randomChar(16);
				this.url = a;
				this.data = d.A.extend({}, b);
				d.isArray(c) ? (this.onComplete = c[0] || new Function, this.onFailure = c[1] || new Function) : (this.onComplete =
					c || new Function, this.onFailure = new Function);
				this.iframeName = e || "POST_IFRAME_" + this.keyName;
				this.url ? this.send() : d.debug.Log("NTKF.POST param(url) is undefined.",
					2)
			},
			send: function() {
				this.iframe = this._iframe(this.iframeName, "about:blank");
				this._form();
				try {
					this.form.submit()
				} catch (a) {
					d.debug.Log("POST:" + a.message, 3)
				}
			},
			stopCall: function() {
				this._stopCall = !0
			},
			_form: function() {
				this.form && d.B.IE6 && (d.$(this.form).remove(), delete this.form);
				this.form || (d.B.OLDIE ? (this.form = document.createElement(
					"<form accept-charset='utf-8' enctype='application/x-www-form-urlencoded' method='POST' style='display: none;'>"
				), v.appendChild(this.form)) : (this.form = document.createElement("form"),
					this.form.style.display = "none", v.appendChild(this.form), this.form.acceptCharset = "utf-8", this.form.enctype =
					"application/x-www-form-urlencoded", this.form.method = "POST"));
				try {
					this.form.action = this.url, this.form.target = this.iframeName, this.form.setAttribute("target", this.iframeName)
				} catch (a) {
					try {
						this.form.target = this.iframeName
					} catch (b) {}
				}
				for (; this.form.firstChild;) this.form.removeChild(this.form.firstChild);
				if (d.isArray(this.data))
					for (var c = 0; c < this.data.length; c++)
						for (var e in this.data[c]) "function" !=
							typeof this.data[c][e] && this._input(e + "[]", this.data[c][e]);
				else
					for (e in this.data)
						if ("function" != typeof this.data[e])
							if (d.isArray(this.data[e]))
								for (c = 0; c < this.data[e].length; c++) this._input(e + "[]", this.data[e][c]);
							else this._input(e, this.data[e])
			},
			_iframe: function(a, b) {
				if (l[a]) return l[a];
				if (l.frames[a]) return l.frames[a];
				var c = this,
					e = null,
					f = d.B.OLDIE ? '<iframe src="' + b + '" name="' + a + '"></iframe>' : "iframe";
				try {
					e = document.createElement(f)
				} catch (h) {
					d.debug.Log(h, 3)
				}
				e.name = a;
				e.setAttribute("name", a);
				e.id = a;
				v.appendChild(e);
				l[a] = e;
				d.B.SAFARI || (e.style.position = "absolute");
				d.$(e).css({
					left: "-10px",
					top: "-10px",
					width: "1px",
					height: "1px",
					visibility: "hidden"
				});
				e.src = b;
				d.E.addEvent(e, d.B.IE ? "readystatechange" : "load", function(b) {
					/^(?:loaded|complete|undefined)$/.test(e.readyState) && !c._loaded && (c._loaded = !0, c._stopCall || c.onComplete
						.call(c, b, c.iframe.name), l.frames[a] = l[a] = null, setTimeout(function() {
							d.$(c.iframe).remove();
							d.$(c.form).remove()
						}, 800))
				});
				d.E.addEvent(e, "error", function(b) {
					c._stopCall || c.onFailure.call(c,
						b, c.iframe.name);
					l.frames[a] = l[a] = null;
					setTimeout(function() {
						d.$(c.iframe).remove();
						d.$(c.form).remove()
					}, 50)
				});
				return e
			},
			_input: function(a, b) {
				var c;
				d.B.OLDIE ? (c = /number|string/.test(typeof b) ? document.createElement("<input type='hidden' name='" + a +
						"' value='" + encodeURIComponent(b).replace(/</ig, "&lt;").replace(/>/ig, "&gt;") + "'>") : document.createElement(
						"<input type='hidden' name='" + a + "' value='" + encodeURIComponent(d.J.toJSONString(b)).replace(/</ig,
							"&lt;").replace(/>/ig, "&gt;") + "'>"), this.form.appendChild(c)) :
					(c = document.createElement("input"), this.form.appendChild(c), c.type = "hidden", c.name = a, c.value =
						/number|string/.test(typeof b) ? encodeURI(b).replace(/</ig, "&lt;").replace(/>/ig, "&gt;") : encodeURI(d.J.toJSONString(
							b)).replace(/</ig, "&lt;").replace(/>/ig, "&gt;"))
			}
		};
		d.pageManage = d.Class.create();
		d.pageManage.prototype = {
			identId: "",
			keyId: "",
			data: null,
			interTimeID: null,
			config: {
				cachePageCount: 0,
				nextChanage: !0
			},
			initialize: function(a, b) {
				this.keyId = "NTKF_PAGE_MANAGE" + (b ? "_" + b.toUpperCase() : "");
				this.identId = this._2shortTime(0,
					8, 13);
				this.config = d.A.extend(this.config, {
					debug: !1,
					pageNum: 3,
					timeout: 1.5,
					inter: 0.8
				}, a);
				this.config.onChanage = this.config.onChanage || new Function;
				this.config.timeout *= 10;
				this.config.inter *= 1E3;
				this.config.debug && d.debug.Log("pageManage.initialize():", 1);
				var c = this;
				this._update();
				this.interTimeID = setInterval(function() {
					setTimeout(function() {
						c._update()
					}, 0)
				}, this.config.inter);
				d.E.addEvent(window, "unload", function() {
					c._remove();
					setTimeout(function() {}, 500)
				})
			},
			getIsLastPage: function() {
				return this.data.m.length
			},
			_get: function() {
				var a = d.Cookie.get(this.keyId) || "{}";
				return d.A.extend({
					m: []
				}, d.J.parseJSON(a))
			},
			_save: function() {
				var a = d.J.toJSONString(this.data);
				d.Cookie.set(this.keyId, a, 0);
				return a
			},
			_remove: function() {
				var a = this._getIndex();
				this.data.m.splice(a, 1);
				this._save()
			},
			_update: function() {
				d.createTimeID();
				this.data = this._get();
				this._clear();
				var a = "update",
					b = this._getIndex();
				this.data.t = d.S.formatDate(0, "h:i:s");
				if (!this.data.m[b])
					if (this.data.m.length < this.config.pageNum) a = "add", this.data.m[b] = {};
					else return;
				this.data.m[b][this.identId] = this._2shortTime();
				this._save();
				this.config.debug && d.debug.Log(this.identId + ",pageCount:" + this.data.m.length + "," + a + " data:" + d.J.toJSONString(
					this.data.m), 1);
				if (("add" == a || !0 !== this.config.nextChanage) && this.config.cachePageCount != this.data.m.length) this.config
					.onChanage.call(this, this.data.m.length, this.data.m), this.config.cachePageCount = this.data.m.length;
				this.config.nextChanage = !1
			},
			_clear: function() {
				var a = this._2shortTime();
				if (this.data.m.length)
					for (var b = 0, c; b < this.data.m.length; b++)
						for (var e in this.data.m[b]) "function" !=
							typeof this.data.m[b][e] && (c = a - parseInt(this.data.m[b][e]), c >= this.config.timeout && (this.data.m.splice(
								b, 1), this.config.nextChanage = !0))
			},
			_getIndex: function() {
				if (!this.data.m.length) return 0;
				for (var a = 0; a < this.data.m.length; a++)
					for (var b in this.data.m[a])
						if (parseInt(b) === parseInt(this.identId)) return a;
				return a
			},
			_2shortTime: function(a, b, c) {
				a = (a ? a : d.createTimeID()).toString();
				return parseInt(a.substring(b || 5, c || 11))
			}
		};
		d.sfn.extend({
			sprintf: function() {
				var a = arguments.length,
					b = this;
				if (1 > a || !RegExp) return b.toString();
				for (var c = 0; c < a; c++) b = b.replace(RegExp("%" + (c + 1), "g"), (arguments[c] + "").replace(/%\d/g, "").toString());
				return b
			},
			linkfilter: function(a) {
				return this.toString().replace(
					/((http|https):\/\/)?([\w-]+\.)([\w-]+\.)([a-zA-Z]+)([\w.\/:%+!@$&#=?\|\-\^*\(\)\,\.]+)?/ig,
					function(b, c, e, d, h, g, i) {
						return '<a href="' + (c || "http://") + d + h + g + (i || "") + '" target="_blank" style="' + a + '">' + (c ||
							"") + d + h + g + (i || "") + "</a>"
					})
			},
			getParam: function(a) {
				var b;
				return (b = this.match(RegExp("(^|&|\\?)" + a + "=([^&]*)(&|$)"))) ? unescape(b[2]) : ""
			},
			trim: function() {
				return this.replace(/(^\s*)|(\s*$)/g, "")
			}
		});
		d.extend({
			String: function(a) {
				a = new String(a || "");
				return a = d.A.extend(a, d.sfn)
			}
		});
		d.fn.extend({
			get: function(a) {
				return this[a] || null
			},
			first: function() {
				return this[0] || null
			},
			last: function() {
				return this[this.length - 1] || null
			},
			find: function(a) {
				return d.$(a, this)
			},
			parent: function() {
				return this[0] ? d.$(this[0].parentNode) : this
			},
			each: function(a) {
				return d.A.each(this, a)
			},
			attr: function(a, b) {
				return "object" == typeof a ? d.A.each(this, function(b) {
					d.A.each(a,
						function(a, f) {
							d.D.attr(b, f, a)
						})
				}) : !d.isDefined(b) ? this[0] ? d.D.attr(this[0], a) || this[0][a] : "" : d.A.each(this, function(c) {
					d.D.attr(c, a, b)
				})
			},
			scrollTop: function(a) {
				if ("undefined" == typeof a) return this[0] ? this[0].scrollTop : 0;
				this[0].scrollTop = a
			},
			scrollLeft: function(a) {
				if ("undefined" == typeof a) return this[0] ? this[0].scrollLeft : 0;
				this[0].scrollLeft = a
			},
			scrollHeight: function(a) {
				if ("undefined" == typeof a) return this[0] ? this[0].scrollHeight : 0;
				this[0].scrollHeight = a
			},
			css: function(a, b) {
				return "object" == typeof a ?
					d.A.each(this, function(b) {
						d.A.each(a, function(a, f) {
							d.D.css(b, f, a)
						})
					}) : !b ? this[0] ? d.D.css(this[0], a) : "" : d.A.each(this, function(c) {
						d.D.css(c, a, b)
					})
			},
			cssText: function(a) {
				return d.A.each(this, function(b) {
					b.style.cssText = a
				})
			},
			rect: function(a) {
				var b = d.D.clientRect(this[0]),
					c = {
						height: "height",
						width: "width",
						left: "left",
						top: "top",
						windowWidth: "viewW",
						windowHeight: "viewH",
						scrollLeft: "viewX",
						scrollTop: "viewY"
					};
				return c[a] ? b[c[a]] : b
			},
			value: function(a) {
				if ("undefined" != typeof a && this[0]) return d.A.each(this, function(b) {
					b.value =
						a
				});
				if (this[0]) return this[0].value
			},
			setTextSelected: function() {
				return d.A.each(this, function(a) {
					var b = b || 0,
						c = c || a.value.length;
					a.setSelectionRange ? (a.focus(), a.setSelectionRange(b, c)) : a.createTextRange && (a = a.createTextRange(),
						a.collapse(!0), a.moveStart("character", b), a.moveEnd("character", c - b - 1), a.select())
				})
			},
			insert: function(a, b) {
				this.first() && d.D.insert(this.first(), a, b);
				return this
			},
			html: function(a) {
				if ("undefined" != typeof a && this[0]) return d.A.each(this, function(b) {
					b.innerHTML = a
				});
				if (this[0]) return this[0].innerHTML
			},
			replace: function(a) {
				this.each(function(b) {
					b.replaceNode ? b.replaceNode(a) : (b.parentNode.appendChild(a), b.parentNode.removeChild(b))
				});
				return d.$(a)
			},
			show: function(a, b) {
				a ? (this.each(function(a) {
					d.D.css(a, "display", "block");
					d.D.css(a, "opacity", 0.01)
				}), d.D.animate(this, {
					opacity: 100
				}, 600, "easeInOutCubic", b)) : this.each(function(a) {
					d.D.css(a, "display", "block")
				});
				return this
			},
			hide: function(a, b, c) {
				var e = this;
				a ? d.D.animate(this, {
					opacity: 1
				}, 600, "easeInOutCubic", function(a) {
					b ? d.$(a).remove() : d.$(a).css({
						display: "none",
						opacity: 1
					});
					c && c.call(e)
				}) : this.each(function(a) {
					d.D.css(a, "display", "none");
					b && d.$(a).remove()
				});
				return this
			},
			anim: function(a, b, c, e) {
				return d.D.animate(this, a, b || 600, c || "easeInOutCubic", e)
			},
			addClass: function(a) {
				return d.A.each(this, function(b) {
					d.D.addClass(b, a)
				})
			},
			removeClass: function(a) {
				return d.A.each(this, function(b) {
					d.D.removeClass(b, a)
				})
			},
			indexOfClass: function(a) {
				return d.D.indexOfClass(this[0], a)
			},
			replaceClass: function(a, b) {
				return d.A.each(this, function(c) {
					d.D.replaceClass(c, a, b)
				})
			},
			remove: function() {
				return d.A.each(this,
					function(a) {
						if (d.B.OLDIE) {
							var b = document.createElement("DIV");
							b.appendChild(a);
							b.innerHTML = "";
							try {
								b.parentNode.removeChild(b)
							} catch (c) {}
						} else try {
							a.parentNode.removeChild(a)
						} catch (e) {}
					})
			},
			bind: function(a, b) {
				var c = this;
				return d.A.each(this, function(e) {
					d.E.addEvent(e, a, function(a) {
						b.call(c, a, this)
					})
				})
			},
			hover: function(a, b) {
				var c = this;
				return this.bind("mouseenter", function(e, f) {
					"string" == typeof a ? d.$(f).replaceClass(b, a) : a.call(c, e, f)
				}).bind("mouseleave", function(e, f) {
					"string" == typeof b ? d.$(f).replaceClass(a,
						b) : b.call(c, e, f)
				})
			},
			toObject: function() {
				return d.D.elementToObject(this[0])
			},
			gradient: function(a, b, c) {
				var e, f;
				return a ? d.A.each(this, function(h) {
					d.B.OLDIE && (e = /top|bottom/.test(a) ? 0 : 1, /right|bottom/.test(a) && (f = b, b = c, c = f));
					if ("gecko" == d.B.CORE) d.D.css(h, "background-image", !a ? "none" : "-moz-linear-gradient(" + a + ", " + b +
						", " + c + ")");
					else if ("webkit" == d.B.CORE) {
						switch (a) {
							case "top":
								e = "0% 100%,0% 0%";
								break;
							case "right":
								e = "0% 0%,100% 0%";
								break;
							case "bottom":
								e = "0% 0%,0% 100%";
								break;
							case "right":
								e = "100% 0%,0% 0%"
						}
						d.D.css(h,
							"background-image", !a ? "none" : "-webkit-gradient(linear," + e + ",color-stop(1, " + b +
							"),color-stop(0, " + c + "))")
					} else d.B.OLDIE ? d.D.css(h, "filter", !a ? "none" :
						"progid:DXImageTransform.Microsoft.gradient(GradientType=" + e + ",startColorstr='" + b +
						"', endColorstr='" + c + "')") : d.B.IE ? d.D.css(h, "background-image", !a ? "none" :
						"-ms-linear-gradient(" + a + ", " + b + ", " + c + ")") : d.D.css(h, "background-image", !a ? "none" :
						"linear-gradient(" + a + ", " + b + ", " + c + ")")
				}) : d.A.each(this, function(a) {
					d.B.OLDIE ? d.D.css(a, "filter", "none") : d.D.css(a,
						"background-image", "none")
				})
			},
			gray: function(a) {
				return d.A.each(this, function(b) {
					if ("about:blank" == b.src) return this;
					"gecko" == d.B.CORE ? a ? d.D.css(b, "opacity", 1) : d.D.css(b, "opacity", 0.75) : "webkit" == d.B.CORE ? a ?
						d.D.css(b, "-webkit-filter", "") : d.D.css(b, "-webkit-filter", "grayscale(100%)") : d.B.OLDIE ? a ? d.D.css(
							b, "filter", "") : d.D.css(b, "filter", "gray") : a ? d.D.css(b, "-ms-filter", "") : d.D.css(b,
							"-ms-filter", "grayscale(100%)")
				})
			}
		});
		d.extend({
			bodyOffset: d.$(document.body).rect(),
			_initialize: function() {
				var a = this,
					b = function() {
						a.bodyOffset = a.$(document.body).rect()
					};
				a.bodyOffset = this.$(document.body).rect();
				this.B.IE && 9 <= this.B.IE_VER && (a.bodyOffset.viewW -= 17);
				this.__init_num = 0;
				j = this.D.existsScroll("scrollTop");
				this.D.existsScroll("scrollLeft");
				this.__init_timeid = setInterval(function() {
					j = a.D.existsScroll("scrollTop");
					a.D.existsScroll("scrollLeft");
					2 < a.__init_num && clearInterval(a.__init_timeid);
					b();
					a.__init_num++
				}, 100);
				a.E.addEvent(l, "resize", b);
				a.E.addEvent(l, "scroll", b);
				return a.bodyOffset
			}
		});
		d.ready(function() {
			d._initialize()
		});
		if (d.B.IE6) try {
			document.execCommand("BackgroundImageCache", !1, !0)
		} catch (A) {}
		window.NTKF = d
	}
})();
NTKF.register("chat_inpage", !0);
(function() {
	NTKF.register("protocol", document.location.protocol);
	NTKF.register("version", "20140305110741");
	NTKF.register("regFileName", "ntkfstat");
	NTKF.register("url_resource", "");
	NTKF.register("url_service", NTKF.protocol + "//downt.ntalker.com/t2d/");
	NTKF.register("url_trail", NTKF.protocol + "//trail.ntalker.com:443/trail/trail");
	NTKF.register("url_manage_server", NTKF.protocol + "//mcenter2.ntalker.com/");
	NTKF.register("url_marketing", NTKF.protocol + "//114.112.83.188:89/promotion");
	NTKF.register("url_ActiveX",
		"http://download.ntalker.com/ntalkerCaptureImage.zip");
	NTKF.register("version_tchat", "20131120172156");
	NTKF.register("version_upload", "20130912142911");
	NTKF.register("version_presence", "20130912165908");
	NTKF.register("version_ntid", "20130329094514");
	NTKF.register("version_ntchat", "20140305110741");
	NTKF.register("version_cometd", "20140305110741");
	NTKF.register("version_entrance", "20130220");
	NTKF.register("version_config", "20140305110741");
	NTKF.register("version_marketing_css", "20140305110741");
	NTKF.extend({
		Lang: {
			language: "zh_cn",
			chat_title_ext: "(Ntalker \u8bd5\u7528\u7248)",
			chat_close_text: "\u5173\u95ed",
			chat_minimize_text: "\u6700\u5c0f\u5316",
			chat_mjump_text: "\u8df3\u51fa",
			chat_link_refresh: "\u5237\u65b0",
			chat_button_send: "\u53d1\u9001",
			toolbar_default_text: "\u5206\u914d\u5ba2\u670d\u4e2d",
			toolbar_allocation_failure: "\u6682\u65e0\u5ba2\u670d",
			load_file: "\u8bf7\u5148\u8f7d\u5165\u811a\u672c %1",
			loading: "\u6b63\u5728\u52a0\u8f7d...{$count}%",
			load_failure: "\u52a0\u8f7d\u5931\u8d25. {$count}%",
			load_no_customerservice: "\u6682\u65e0\u5e94\u7b54\u5ba2\u670d.",
			go_reconnect: "\u91cd\u65b0\u5f00\u59cb",
			go_message: "\u7559\u8a00",
			labeltext: "\u5546\u54c1,\u95ee\u9898,\u5173\u4e8e",
			news_before_evaluation: "\u60a8\u8fd8\u6ca1\u6709\u5bf9\u5ba2\u670d\u670d\u52a1\u8fdb\u884c\u8bc4\u4ef7\uff0c\u786e\u5b9a\u79bb\u5f00\u5f53\u524d\u9875\u9762\u5417\uff1f",
			news_evaluation: "\u60a8\u7684\u8bc4\u4ef7\u201c{$evaluation}\u201d\u5df2\u7ecf\u63d0\u4ea4\u3002",
			news_ready_evaluation: "\u60a8\u5df2\u5bf9\u6b64\u5ba2\u670d\u8bc4\u4ef7\u8fc7\u4e86",
			news_min_text: "\u4f60\u6709{$count}\u6761\u65b0\u6d88\u606f",
			news_connect_complete: "\u5ba2\u670d {$destname} \u4e3a\u60a8\u670d\u52a1\uff01",
			news_connect_complete_mobile: "\u4f60\u4eec\u73b0\u5728\u53ef\u4ee5\u5f00\u59cb\u5bf9\u8bdd\u5566",
			news_interrupted: "\u60a8\u7684\u8fde\u7ebf\u5df2\u4e2d\u65ad,\u8bf7 [link reconnect={$settingid}]\u91cd\u8bd5[/link] \u6216 [link message={$settingid} source=3]\u7559\u8a00[/link],\u6211\u4eec\u4f1a\u5c3d\u5feb\u56de\u590d\u60a8",
			news_auto_interrupted: "\u60a8\u592a\u957f\u65f6\u95f4\u6ca1\u53d1\u8a00\uff0c\u7cfb\u7edf\u81ea\u52a8\u7ed3\u675f\u672c\u6b21\u901a\u8bdd\uff0c\u8bf7[link reconnect={$settingid}]\u70b9\u51fb\u91cd\u65b0\u8fde\u63a5[/link]\u7ee7\u7eed\u54a8\u8be2\u6216 [link message={$settingid} source=5]\u7559\u8a00[/link]\u3002",
			news_session_end: "\u4f1a\u8bdd\u5df2\u7ed3\u675f",
			news_not_online: "\u60a8\u597d!\u73b0\u5728\u5ba2\u670d {$destname} \u4e0d\u5728\u7ebf,\u8bf7 [link message={$settingid} source=5]\u7559\u8a00[/link],\u6211\u4eec\u4f1a\u5c3d\u5feb\u56de\u590d\u60a8\uff01",
			news_queue: "\u60a8\u524d\u9762\u8fd8\u6709 {$count} \u4f4d\u8bbf\u5ba2\u6392\u961f\u4e2d [{$time}]{$br}\u5982\u679c\u60a8\u4e0d\u60f3\u7b49\u5f85,\u8bf7 [link message={$settingid} source=2]\u7559\u8a00[/link],\u6211\u4eec\u4f1a\u5c3d\u5feb\u56de\u590d\u60a8\uff01",
			news_queue_no_message: "\u60a8\u524d\u9762\u8fd8\u6709 {$count} \u4f4d\u8bbf\u5ba2\u6392\u961f\u4e2d [{$time}]",
			news_offline: "\u5ba2\u670d {$destname} \u5df2\u79bb\u7ebf,\u8bf7 [link message={$settingid} source=4]\u7559\u8a00[/link] \u6216 [link reconnect={$settingid}]\u9009\u62e9\u5176\u4ed6\u5ba2\u670d[/link] \u5bf9\u8bdd",
			news_leave: "\u5ba2\u670d {$destname} \u5df2\u79bb\u5f00,\u5efa\u8bae [link reconnect={$settingid}]\u91cd\u65b0\u5206\u914d\u5ba2\u670d[/link]",
			news_adapter: "\u8bf7\u5e2e\u6211\u5c06\u4f1a\u8bdd\u8f6c\u63a5\u5230\u5176\u4ed6\u5ba2\u670d\uff0c\u8c22\u8c22\uff01",
			news_start_trans: "\u5f00\u59cb\u4e0a\u4f20{$type} \u201c{$file}\u201d \uff0c\u8bf7\u7a0d\u5019... [link cancel={$settingid}]\u53d6\u6d88[/link]",
			news_send_file: "\u6587\u4ef6 {$file} ({$size}) {$message}",
			news_receive_file: "\u6587\u4ef6 {$file} ({$size}) {$message} [link {$url}]\u4fdd\u5b58[/link]",
			cancel_trans_text: "\u53d6\u6d88",
			news_cancel_trans: "\u5df2\u53d6\u6d88{$type}\u4f20\u8f93",
			news_trans_complete: "\u4f20\u8f93\u5b8c\u6210\u3002",
			news_receiver_complete: "\u63a5\u6536\u5b8c\u6210\u3002",
			news_send_return: "\u60a8\u8bf4\u5f97\u592a\u5feb\u4e86\uff0c\u8bf7\u4f11\u606f\u4e00\u4f1a\u513f\u5427\uff01",
			news_send_failure: "\u6d88\u606f\u201c{$message}\u201d\u53d1\u9001\u5931\u8d25\uff01",
			news_new: "\u65b0\u6d88\u606f",
			input_state: "\u5bf9\u65b9\u6b63\u5728\u8f93\u5165......",
			connect_state: "\u6b63\u5728\u8fde\u63a5\u670d\u52a1\u5668\uff0c\u8bf7\u8010\u5fc3\u7b49\u5019\u3002",
			connect_state1: "\u53ef\u80fd\u7531\u4e8e\u7f51\u7edc\u539f\u56e0\uff0c\u6d88\u606f\u201c{$message}\u201d\u53d1\u9001\u5931\u8d25\u3002",
			connect_timeout: "\u8fde\u63a5\u8d85\u65f6\uff0c\u53d1\u9001\u6d88\u606f\u5931\u8d25",
			button_file: "\u6587\u4ef6",
			button_image: "\u56fe\u7247",
			button_face: "\u8868\u60c5",
			button_save: "\u4fdd\u5b58",
			button_captureImage: "\u622a\u56fe",
			button_evaluation: "\u8bc4\u4ef7",
			button_reply: "\u56de\u590d",
			button_consult: "\u7acb\u523b\u54a8\u8be2",
			default_evaluation_form_title: "\u670d\u52a1\u8bc4\u4ef7",
			default_evaluation_form_fields: [{
				title: "\u611f\u8c22\u60a8\u7684\u652f\u6301\uff0c\u4e3a\u4e86\u4f7f\u6211\u4eec\u66f4\u597d\u7684\u4e3a\u60a8\u670d\u52a1\uff0c\u8bf7\u60a8\u7ed9\u672c\u6b21\u670d\u52a1\u4e00\u4e2a\u8bc4\u4ef7\u3002\u60a8\u5bf9\u672c\u6b21\u670d\u52a1\u7684\u8bc4\u4ef7\u662f\uff1f",
				name: "evaluation",
				type: "radio",
				required: !0,
				defaultText: "",
				options: [{
					text: "\u975e\u5e38\u6ee1\u610f",
					value: "5"
				}, {
					text: "\u6ee1\u610f",
					value: "4"
				}, {
					text: "\u4e00\u822c\u822c",
					value: "3"
				}, {
					text: "\u5f88\u5dee",
					value: "2"
				}, {
					text: "\u592a\u5dee\u52b2\u513f",
					value: "1"
				}],
				message: ["\u8bf7\u5bf9\u5ba2\u670d\u8fdb\u884c\u8bc4\u4ef7", ""]
			}, {
				title: "\u5efa\u8bae",
				multipart: !0,
				name: "proposal",
				type: "textarea",
				defaultText: "\u60a8\u5982\u679c\u5bf9\u6211\u4eec\u7684\u670d\u52a1\u6709\u4ec0\u4e48\u610f\u4ef6\u6216\u5efa\u8bae\uff0c\u8bf7\u5728\u8fd9\u91cc\u8be6\u7ec6\u8bf4\u660e\u4e00\u4e0b\u3002",
				required: !1,
				max: 400,
				message: ["", "\u5efa\u8bae\u5185\u5bb9\u4e0d\u80fd\u8d85\u8fc7200\u4e2a\u4e2d\u6587\u5b57\u7b26"]
			}],
			default_message_form_fields: [{
				title: "\u59d3\u540d",
				name: "msg_name",
				type: "text",
				defaultText: "\u8bf7\u586b\u5199\u60a8\u7684\u771f\u5b9e\u59d3\u540d",
				required: !0,
				message: ["\u8bf7\u8f93\u5165\u60a8\u7684\u6635\u79f0"]
			}, {
				title: "\u7535\u8bdd",
				name: "msg_tel",
				type: "text",
				defaultText: "\u8bf7\u586b\u5199\u56fa\u5b9a\u6216\u79fb\u52a8\u7535\u8bdd\u53f7\u7801",
				required: !0,
				verification: "phone",
				message: ["\u8bf7\u586b\u5199\u7535\u8bdd\u53f7\u7801", "\u7535\u8bdd\u53f7\u7801\u683c\u5f0f\u9519\u8bef"]
			}, {
				title: "\u90ae\u7bb1",
				name: "msg_email",
				type: "text",
				defaultText: "",
				required: !1,
				verification: "email",
				message: ["", "\u7535\u5b50\u90ae\u7bb1\u5730\u5740\u683c\u5f0f\u9519\u8bef"]
			}, {
				title: "\u7559\u8a00",
				name: "msg_content",
				type: "textarea",
				defaultText: "\u8bf7\u5c06\u60a8\u7684\u95ee\u9898\u8be6\u7ec6\u5199\u4e0b\uff0c\u6211\u4eec\u4f1a\u5c3d\u5feb\u4e0e\u60a8\u8054\u7cfb\u3002",
				required: !0,
				message: ["\u7559\u8a00\u5185\u5bb9\u4e0d\u80fd\u4e3a\u7a7a",
					"\u7559\u8a00\u5185\u5bb9\u4e0d\u80fd\u8d85\u8fc7200\u4e2a\u4e2d\u6587\u5b57\u7b26"
				]
			}],
			default_submitinfo_form_title: "",
			default_submitinfo_form_fields: [{
				title: "\u95ee\u3000\u3000\u9898",
				name: "tips_question",
				type: "text",
				defaultText: "",
				required: !0,
				message: ["\u8bf7\u8f93\u5165\u95ee\u9898\u6807\u9898"]
			}, {
				title: "\u95ee\u9898\u7c7b\u578b",
				name: "tips_type",
				type: "select",
				defaultText: "-\u8bf7\u9009\u62e9\u95ee\u9898\u7c7b\u578b-",
				required: !0,
				options: ["\u552e\u524d\u54a8\u8be2", "\u9000\u6362\u8d27\u670d\u52a1",
					"\u7269\u6d41\u54a8\u8be2", "\u7f3a\u8d27\u5efa\u8bae"
				]
			}, {
				title: "\u5ba2\u6237\u59d3\u540d",
				name: "tips_name",
				type: "text",
				defaultText: "",
				required: !1
			}, {
				title: "\u8054\u7cfb\u7535\u8bdd",
				name: "tips_phone",
				type: "text",
				defaultText: "",
				required: !1,
				verification: "phone"
			}, {
				title: "\u7535\u5b50\u90ae\u4ef6",
				name: "tips_email",
				type: "text",
				defaultText: "",
				required: !1,
				verification: "email"
			}],
			message_success: "\u7559\u8a00\u63d0\u4ea4\u6210\u529f\uff01",
			message_no_null: "*",
			editorFaceAlt: {
				1: "\u5fae\u7b11",
				2: "\u5bb3\u7f9e",
				3: "\u8272",
				4: "\u5077\u7b11",
				5: "\u5f97\u610f",
				11: "\u60ca\u8bb6",
				9: "\u7761\u89c9",
				8: "\u95ed\u5634",
				6: "\u6d41\u6cea",
				10: "\u5927\u54ed",
				21: "\u5f00\u5fc3",
				15: "\u73ab\u7470",
				23: "\u9b3c\u8138",
				24: "\u9965\u997f",
				32: "\u7591\u95ee",
				29: "\u5927\u5175",
				20: "\u5c34\u5c2c",
				43: "\u53d1\u6012",
				17: "\u611f\u5192",
				19: "\u5455\u5410",
				31: "\u771f\u68d2",
				34: "\u53d1\u5446",
				44: "\u5c0f\u4e11",
				38: "\u5618",
				13: "\u518d\u89c1",
				22: "\u95ea\u4eba",
				41: "\u51b7",
				28: "\u9119\u89c6",
				36: "\u8870",
				35: "\u5927\u6012",
				57: "\u9c9c\u82b1",
				58: "\u51cb\u8c22",
				53: "\u5f3a",
				54: "\u5f31",
				55: "\u63e1\u624b",
				56: "ok",
				49: "\u5634\u5507",
				47: "\u7231\u5fc3",
				48: "\u5fc3\u788e",
				59: "\u793c\u7269"
			},
			allEditorFaceAlt: {
				1: "\u5fae\u7b11",
				2: "\u5bb3\u7f9e",
				3: "\u8272",
				4: "\u5077\u7b11",
				5: "\u5f97\u610f",
				6: "\u6d41\u6cea",
				7: "\u96be\u8fc7",
				8: "\u95ed\u5634",
				9: "\u7761\u89c9",
				10: "\u5927\u54ed",
				11: "\u60ca\u8bb6",
				12: "\u6655\u5012",
				13: "\u518d\u89c1",
				14: "\u6124\u6012",
				15: "\u73ab\u7470",
				16: "\u4f50\u7f57",
				17: "\u611f\u5192",
				18: "\u6293\u72c2",
				19: "\u5455\u5410",
				20: "\u5c34\u5c2c",
				21: "\u5f00\u5fc3",
				22: "\u95ea\u4eba",
				23: "\u9b3c\u8138",
				24: "\u9965\u997f",
				25: "\u56f0",
				26: "\u60ca\u6050",
				27: "\u64e6\u6c57",
				28: "\u9119\u89c6",
				29: "\u5927\u5175",
				30: "\u5355\u6311",
				31: "\u771f\u68d2",
				32: "\u7591\u95ee",
				33: "\u6297\u8bae",
				34: "\u53d1\u5446",
				35: "\u5927\u6012",
				36: "\u8870",
				37: "\u9ab7\u9ac5",
				38: "\u5618",
				39: "\u5929\u4f7f",
				40: "\u6076\u9b54",
				41: "\u51b7",
				42: "\u7231",
				43: "\u53d1\u6012",
				44: "\u5c0f\u4e11",
				45: "\u5927\u4fbf",
				46: "\u732a\u5934",
				47: "\u7231\u5fc3",
				48: "\u5fc3\u788e",
				49: "\u5634\u5507",
				50: "\u7cd6\u679c",
				51: "\u86cb\u7cd5",
				52: "\u70b8\u5f39",
				53: "\u5f3a",
				54: "\u5f31",
				55: "\u63e1\u624b",
				56: "ok",
				57: "\u9c9c\u82b1",
				58: "\u51cb\u8c22",
				59: "\u793c\u7269",
				60: "\u5403\u996d"
			},
			flash_title: "\u83b7\u5f97\u4f18\u60e0\u52b5\uff01",
			coupon_title: "\u9886\u53d6\u4f18\u60e0\u52b5",
			coupon_mobile: "\u624b \u673a\uff1a",
			coupon_mobile_default_text: "\u8bf7\u8f93\u5165\u624b\u673a\u53f7",
			coupon_mobile_error_text: "\u53f7\u7801\u6709\u8bef",
			coupon_name: "\u59d3 \u540d\uff1a",
			coupon_name_default_text: "\u8bf7\u8f93\u5165\u59d3\u540d",
			coupon_name_error_text: "\u59d3\u540d\u6709\u8bef",
			coupon_button_receive: "\u9886 \u53d6",
			coupon_button_complete: "\u5b8c \u6210",
			coupon_button_close: "\u5173 \u95ed",
			coupon_send_complete: "\u4f18\u60e0\u5238\u77ed\u4fe1\u53d1\u9001\u6210\u529f",
			tips_submit_news: "\u4e3a\u4e86\u66f4\u597d\u5730\u4e3a\u60a8\u670d\u52a1\uff0c\u8bf7\u586b\u5199\u4e0b\u5217\u4fe1\u606f\uff0c\u6211\u4eec\u5c06\u7acb\u523b\u4e3a\u60a8\u8fde\u63a5\u9996\u5e2d\u5ba2\u670d\u4e13\u5458\u3002",
			tips_button_text: "\u5f00\u59cb\u54a8\u8be2",
			tips_evaluation_button_text: " \u8bc4\u4ef7 "
		}
	})
})();
(function() {
	if ("undefined" === typeof window.NTKF.t2d) {
		var l = window,
			v = document.documentElement || document.body,
			y = window.location,
			x = 0,
			m = !1,
			j = NTKF.$,
			d = NTKF.A,
			t = NTKF.B,
			n = NTKF.C,
			s = NTKF.D,
			B = NTKF.J,
			p = NTKF.Lang,
			A = NTKF.S,
			a = NTKF.debug,
			b = NTKF.globalConfig,
			c = NTKF.require;
		NTKF.extend({
			initGlobalVariable: function(a) {
				if (!NTKF.ntkfInited && (NTKF.ntkfInited = !0, NTKF.debug.Log("NTKF.initGlobalVariable()"), "undefined" ==
						typeof NTKF_PARAM && (NTKF_PARAM = {}, NTKF.debug.Log("NTKF_PARAM not define", 2)), NTKF_PARAM.uid && (
							NTKF_PARAM.uid =
							NTKF_PARAM.uid.toString().replace(/undefined/ig, "")), b.CONNECT_TYPE = NTKF.Flash.supportFlash && !NTKF.B.MOBILE ?
						"FLASH" : "COMETD", j("script").each(function(a) {
							var b, e;
							e = RegExp("(https?:)(//.*?)/((ntkfstat|ntkf_core|ntkf)(.*)?.js)", "gi");
							b = decodeURIComponent(a.getAttribute("src") || "");
							e = e.exec(b);
							/(ntkfstat)|(ntkf)|(ntkf_core)/i.test(b) && (b = NTKF.String(b), x = NTKF.params["ntalker-preview"], NTKF.url_resource =
								NTKF.String(NTKF.protocol + e[2]).trim(), NTKF.url_protocol = NTKF.String(e[1]).trim(), NTKF.url_name =
								NTKF.String(e[3]).trim(),
								NTKF.Mode = b.getParam("mode"), NTKF.siteid = NTKF_PARAM.siteid || b.getParam("siteid") || "", NTKF.settingid =
								NTKF_PARAM.settingid || b.getParam("settingid") || "", NTKF.sellerid = NTKF_PARAM.sellerid || b.getParam(
									"sellerid") || "", NTKF.user.shortID = NTKF_PARAM.uid && "0" != NTKF_PARAM.uid && "undefined" !=
								NTKF_PARAM.uid ? NTKF_PARAM.uid.toString().replace("#", "@") : b.getParam("uid") && "0" != b.getParam(
									"uid") && "undefined" != b.getParam("uid") ? b.getParam("uid") : "", NTKF.user.name = NTKF_PARAM.uname &&
								"null" != NTKF_PARAM.uname ? NTKF_PARAM.uname.toString().replace("#",
									"@") : b.getParam("uname"), NTKF.globalConfig = d.extend(NTKF.globalConfig, {
									statictis: NTKF_PARAM.statictis || 2,
									CONNECT_LEVEL: NTKF_PARAM.isnoim || 0,
									notrail: NTKF_PARAM.isnotrail || 0,
									entrance: NTKF_PARAM.entrance || 0,
									position: NTKF_PARAM.position || {},
									fixed: NTKF_PARAM.fixed || !0,
									lang: NTKF_PARAM.lang || NTKF.lang,
									userlevel: NTKF_PARAM.userlevel || 0,
									pagetype: NTKF_PARAM.pagetype || "",
									itemid: NTKF_PARAM.itemid || "",
									itemparam: NTKF_PARAM.itemparam || NTKF.params.source || "",
									orderid: NTKF_PARAM.orderid || "",
									orderprice: NTKF_PARAM.orderprice ||
										"",
									erpparam: NTKF_PARAM.erpparam || "",
									params: NTKF_PARAM.params || "",
									ntalkerparam: NTKF_PARAM.ntalkerparam || {},
									proinfo: NTKF_PARAM.ntalkerparam && NTKF_PARAM.ntalkerparam.item ? NTKF_PARAM.ntalkerparam.item : null
								}), NTKF.title = NTKF_PARAM.pagetitile || NTKF.title.substr(0, 32), NTKF.parentNode = a.parentNode)
						}), !1 !== d.inArray(["kf_9933", "kf_9923", "kf_9954", "kf_10111"], NTKF.siteid) && (NTKF.globalConfig.CONNECT_LEVEL =
							2), !1 !== d.inArray(["kf_9933", "kf_9949", "kf_9954"], NTKF.siteid) && (NTKF.user.shortID = NTKF.user.name),
						"lazy" !=
						NTKF.Mode || a))
					if (NTKF.url_resource)
						if (!NTKF.siteid || !NTKF.settingid) NTKF.debug.Log("siteid:" + NTKF.siteid + ", settingid:" + NTKF.settingid,
							3);
						else {
							NTKF.imagesResources.url_image = NTKF.url_resource + "/images2.0";
							NTKF.user.id = "" == NTKF.user.shortID ? "" : NTKF.siteid + "_ISME9754_" + NTKF.user.shortID;
							this.url_source = NTKF.chat_inpage ? y.href : document.referrer || l.opener && l.opener.location.href || "";
							if ("" == this.url_source) try {
								this.url_source = l.parent && l.parent.location.href != y.href ? l.parent.location.href : ""
							} catch (c) {
								NTKF.debug.Log("get url_source: " +
									c.message, 3)
							}
							NTKF.lastPageManage = new NTKF.pageManage({
								debug: !1,
								onChanage: function(a) {
									NTKF.curOpenWindow = a
								}
							}, "");
							NTKF.t2d.initialize()
						}
				else NTKF.debug.Log("Failed to obtain an address.", 3)
			},
			im_start: function() {
				this.initGlobalVariable(!0)
			},
			autoOpenInviteWindow: function() {
				if ((this.config = d.extend({}, NTKF.defaultConfig)) && 1 == this.config.autoinvite && 2 === NTKF.IMConnectStatus) {
					var a = this,
						b;
					b = a.config.icon ? a.config.icon.members.groupID : a.config.list ? a.config.list.members.groupID : a.config.toolbar ?
						a.config.toolbar.members.groupID :
						0;
					/ntkf_core/i.test(NTKF.url_name) ? c(function() {
						NTKF.oldInvite.timer = setTimeout(function() {
							NTKF.oldInvite.showWindow(b, a.config.invitecontent);
							NTKF.promptwindow.startPrompt("", p.news_new, !0)
						}, a.config.invitedelay)
					}, "chatEntrance.js?version=" + NTKF.version_entrance, NTKF.oldInvite) : c(function() {
						NTKF.inviteTimeID = setTimeout(function() {
							NTKF.invite.showTips(b, a.config.invitecontent);
							NTKF.promptwindow.startPrompt("", p.news_new, !0)
						}, a.config.invitedelay)
					}, "ntchat.js?version=" + NTKF.version_ntchat, NTKF.chatManage)
				}
			},
			im_openInPageChat: function(e, d, h, g, i, k) {
				!0 != k && (b.wait = 6, clearTimeout(b.waitTimeID));
				(!NTKF.user.id || !NTKF.defaultConfig) && 0 < b.wait ? b.waitTimeID = setTimeout(function() {
					b.wait--;
					NTKF.im_openInPageChat(e, d, h, g, i, !0)
				}, 500) : (e = e || (NTKF.defaultConfig ? NTKF.defaultConfig.settingid : ""), a.Log("NTKF.im_openInPageChat(" +
						(e ? '"' + e + '"' : "") + (d ? ', "' + d + '"' : "") + (h ? ', "' + h + '"' : "") + ")," + NTKF.user.id, 1),
					NTKF.ntChattype = NTKF.ntChattype ? NTKF.ntChattype : 1, NTKF.B.IE6 && !NTKF.B.CSS1Compat ? NTKF.debug.Log(
						"IE6 && not exist DOCTYPE",
						3) : b.newwindow || NTKF.B.MOBILE ? NTKF.t2d.openChatWindow(h, "", e, "", d, i) : c(function() {
						NTKF.chatManage.open(e, d, h, g, i);
						NTKF.CACHE.save("opd", 1)
					}, "ntchat.js?version=" + NTKF.version_ntchat, NTKF.chatManage))
			},
			enableDebug: function(a) {
				NTKF.debug.enable = a || 2;
				return NTKF.CACHE.save("d.e", a || 2)
			},
			t2d: {
				chatFlashGoUrl: null,
				openedInWind: null
			},
			imagesResources: {
				url_image: "",
				url_chat_image: "",
				url_mobile_image: "",
				resources: []
			}
		});
		NTKF.t2d.extend({
			initialize: function() {
				NTKF.debug.Log("NTKF.t2d.initialize()");
				var b = this,
					c = NTKF.settingid.substr(0, NTKF.settingid.lastIndexOf("_")) + "_" + NTKF.settingid + ("1" == x ? "_temp" :
						"") + ".js?version=" + NTKF.version;
				n.load(NTKF.url_service + "config/" + c, function(c) {
					"complete" == c.status ? "undefined" !== typeof NTKF.CONFIG ? (NTKF.defaultConfig = d.extend(NTKF.CONFIG, {
								settingid: NTKF.settingid
							}), NTKF.defaultConfig.service = A.protocolFilter(NTKF.defaultConfig.service), NTKF.defaultConfig.enterprise =
							A.protocolFilter(NTKF.defaultConfig.enterprise), NTKF.defaultConfig.position = d.extend({}, NTKF.defaultConfig
								.position,
								NTKF.globalConfig.position), NTKF.globalConfig.CONNECT_LEVEL = NTKF.defaultConfig.isnoim || NTKF.globalConfig
							.CONNECT_LEVEL, b.initUserInfo(), b.createConnect(), b.preLoadOrCreate(NTKF.globalConfig.newwindow || 0 ===
								NTKF.defaultConfig.preload ? 0 : NTKF.defaultConfig.preload || 2E3, NTKF.defaultConfig.precreate || 2E3)) :
						a.Log("Configuration file failed to load", 3) : NTKF.debug.Log("Fail to load a configuration file", 3)
				});
				NTKF.E.addEvent(l, "beforeunload", function() {
					if (NTKF.invite && !NTKF.invite.Processed && NTKF.invite.requestUid) {
						try {
							NTKF.IMPRESENCE.refuseInvite(NTKF.invite.requestUid,
								2)
						} catch (a) {}
						NTKF.invite.hidenWindow && NTKF.invite.hidenWindow()
					}
					try {
						NTKF.IMPRESENCE.closePresence(), NTKF.debug.Log("flash.closePresence()")
					} catch (b) {}
					NTKF.Flash.remove("#ntkf_flash_impresence");
					NTKF.C.load(NTKF.url_tChatFlashGoUrl);
					setTimeout(function() {}, 1E3)
				})
			},
			initUserInfo: function() {
				var a = NTKF.CACHE.get("uid"),
					b = "" != NTKF.url_referrer && -1 == NTKF.url_referrer.indexOf(NTKF.domain);
				NTKF.globalConfig.trailID = NTKF.CACHE.get("tid");
				NTKF.globalConfig.machineid = NTKF.Cookie.get("NTKF_T2D_CLIENTID");
				b || !NTKF.globalConfig.trailID ||
					!NTKF.globalConfig.machineid ? NTKF.globalConfig.islogin = 1 : "" == NTKF.user.id ? a ? -1 < a.indexOf("guest") &&
					-1 < a.indexOf(NTKF.siteid) ? (NTKF.user.id = a, NTKF.globalConfig.islogin = 0) : NTKF.globalConfig.islogin =
					1 : NTKF.globalConfig.islogin = 1 : NTKF.globalConfig.islogin = !a || NTKF.user.id != a ? 1 : 0;
				NTKF.globalConfig.islogin || this.callTrail(NTKF.user.id, NTKF.globalConfig.machineid, 0)
			},
			preLoadOrCreate: function(a) {
				var b, c = NTKF.Cookie.get("NTKF_T2D_sessionCarry");
				NTKF.Cookie.del("NTKF_T2D_sessionCarry");
				try {
					b = NTKF.J.parseJSON(c)
				} catch (g) {}
				b &&
					setTimeout(function() {
						NTKF.globalConfig = d.extend(NTKF.globalConfig, {
							destid: b.id || "",
							destsid: b.sid || "",
							itemid: b.itemid || ""
						});
						NTKF.im_openInPageChat(b.settingid, NTKF.globalConfig.itemid, NTKF.globalConfig.destid, {
							htmlsid: b.htmlsid,
							single: "undefined" == typeof b.single ? "-1" : b.single
						})
					}, 2E3);
				a && (NTKF.preLoadedTimeID = setTimeout(function() {
						clearTimeout(NTKF.preLoadedTimeID);
						NTKF.require(function() {
							NTKF.debug.Log("Wait " + a + " milliseconds pre-loaded scripts", 1)
						}, "ntchat.js?version=" + NTKF.version_ntchat, NTKF.chatManage)
					},
					a))
			},
			callTrail: function(b, c, h) {
				if (!m && !NTKF.globalConfig.notrail) {
					var g = this,
						i, k = [],
						j = {
							ntalkerparam: NTKF.globalConfig.ntalkerparam
						},
						l = {
							action: "save",
							url: NTKF.url,
							siteid: NTKF.siteid,
							sellerid: NTKF.sellerid,
							uid: b,
							uname: NTKF.user.name,
							userlevel: NTKF.globalConfig.userlevel
						};
					NTKF.globalConfig.pagetype && (l.url = -1 < NTKF.url.indexOf("#") ? -1 < NTKF.url.indexOf("?") ? NTKF.url.replace(
							"#", "&ntalker-pagetype=" + NTKF.globalConfig.pagetype + "#") : NTKF.url.replace("#", "?ntalker-pagetype=" +
							NTKF.globalConfig.pagetype + "#") :
						NTKF.url + "#ntalker-pagetype=" + NTKF.globalConfig.pagetype);
					1 == h ? (NTKF.globalConfig.trailID = NTKF.globalConfig.trailID || NTKF.createTimeID(2), NTKF.CACHE.save("uid",
							b), NTKF.CACHE.save("tid", NTKF.globalConfig.trailID), NTKF.Cookie.set("NTKF_T2D_CLIENTID", c, 63072E6),
						NTKF.debug.Log("LOGIN trailID:" + NTKF.globalConfig.trailID + ", uid:" + b + ", machineID:" + c, 1), l = d.extend(
							l, {
								cid: c,
								sid: NTKF.globalConfig.trailID,
								log: 1,
								lan: NTKF.lang,
								scr: NTKF.version.substr(4, 8) + "|" + screen.width + "*" + screen.height,
								cookie: NTKF.Cookie.enable() ?
									1 : 0,
								flash: NTKF.Flash.version
							})) : (NTKF.debug.Log("LINK trailID:" + NTKF.globalConfig.trailID + ", uid:" + b + ", machineID:" + c, 1),
						l = d.extend(l, {
							cid: c,
							sid: NTKF.globalConfig.trailID,
							log: 0
						}));
					NTKF.isArray(NTKF.globalConfig.orderid) && NTKF.globalConfig.orderid.length != NTKF.globalConfig.orderprice.length ?
						NTKF.debug.Log("$orderid and $orderprice are different types or lengths", 3) : (NTKF.isArray(NTKF.globalConfig
								.orderid) && NTKF.isArray(NTKF.globalConfig.orderprice) ? d.each(NTKF.globalConfig.orderid, function(a, b) {
								k.push(d.extend({},
									l, {
										orderid: a,
										orderprice: NTKF.globalConfig.orderprice[b],
										ttl: NTKF.title,
										ref: NTKF.url_referrer
									}))
							}) : k.push(d.extend({}, l, {
								orderid: NTKF.globalConfig.orderid,
								orderprice: NTKF.globalConfig.orderprice,
								ttl: NTKF.title,
								ref: NTKF.url_referrer
							})), i = !1 !== d.inArray(["kf_9933"], NTKF.siteid) ? NTKF.protocol +
							"//mbaobao.trail.ntalker.com:443/trail/trail/userinfo.php?" : !1 !== d.inArray(["kf_9923"], NTKF.siteid) ?
							NTKF.protocol + "//yougou.trail.ntalker.com:443/trail/trail/userinfo.php?" : !1 !== d.inArray(["kf_9988"],
								NTKF.siteid) ?
							NTKF.protocol + "//trailsvc.ntalker.com/trailsvc/count/trail.php?" : !1 !== d.inArray(["kf_10111"], NTKF.siteid) ?
							NTKF.protocol + "//ganji.trail.ntalker.com/trail/trail/userinfo.php?" : !1 !== d.inArray(["kf_9910"], NTKF.siteid) ?
							NTKF.protocol + "//zbird.trail.ntalker.com:443/trail/trail/userinfo.php?" : NTKF.url_trail +
							"/userinfo.php?", d.each(k, function(b, e) {
								g._callTrail(i + d.toURI(b), j, function(b) {
									a.Log(b, 1)
								}, e)
							}), m = !0)
				}
			},
			_callTrail: function(a, b, c, d) {
				var i = this;
				NTKF.isEmptyObject(b.ntalkerparam) ? n.load(a + "#rnd", function() {
					c.call(i,
						"Trail: script get complete.")
				}) : this.sendTrail = new NTKF.POST(a, b, function(a, b) {
					c.call(i, "Trail: iframe[" + b + "] post complete.")
				}, "POST_IFRAME_" + d)
			},
			getDestUid: function(a, b, c) {
				var g = this,
					i = function(a, b) {
						var e = "";
						a.icon && a.icon.members ? (e = b && -1 < d.inArray(a.icon.members.idList, b) ? b : a.icon.members.groupID, c
							.call(g, e)) : a.list && a.list.members ? (e = b && -1 < d.inArray(a.list.members.idList, b) ? b : a.list.members
							.groupID, c.call(g, e)) : a.toolbar && a.toolbar.members ? (e = b && -1 < d.inArray(a.toolbar.members.idList,
								b) ? b : a.toolbar.members.groupID,
							c.call(g, e)) : a.featureset && a.featureset.members && (e = b && -1 < d.inArray(a.featureset.members.idList,
							b) ? b : a.featureset.members.groupID, c.call(g, e))
					};
				"" == a || a == NTKF.defaultConfig.settingid ? i(NTKF.defaultConfig, b) : (a = a.substr(0, a.lastIndexOf("_")) +
					"_" + a + ".js?version=" + NTKF.version + "#buffer", n.load(NTKF.url_service + "/config/" + a, function(a) {
						"complete" == a.status ? i(NTKF.CONFIG, b) : NTKF.debug.Log("Fail to load a configuration file", 3)
					}))
			},
			openChatWindow: function(a, b, c, d, i, k) {
				function j() {
					NTKF.String(NTKF.url_referrer);
					var a = "width=670,height=460,top=" + (NTKF.bodyOffset.viewH - 460) / 2 + ",left=" + (NTKF.bodyOffset.viewW -
						670) / 2 + ",scrollbars=0,resizable=1,status=1,toolbar=0,menubar=0,location=1";
					this.ChatWindow = window.open(NTKF.url_resource + "/" + (NTKF.B.MOBILE ? "mobile" : "nt") + "chat" + ("zh_cn" ==
						NTKF.Lang.language ? "" : "_en_us") + ".html?" + NTKF.A.toURI({
						settingid: c || NTKF.defaultConfig.settingid,
						destid: NTKF.globalConfig.destid,
						sid: b || NTKF.globalConfig.destsid,
						myuid: NTKF.user.id,
						myuname: NTKF.user.name,
						chattype: d || "",
						itemid: i || (NTKF.globalConfig.itemid ?
							NTKF.globalConfig.itemid : NTKF.globalConfig.proinfo ? NTKF.globalConfig.proinfo.id : ""),
						itemparam: k || NTKF.globalConfig.itemparam,
						erpparam: NTKF.globalConfig.erpparam,
						machineid: NTKF.globalConfig.machineid,
						single: -1 < NTKF.globalConfig.destid.indexOf("GT2D") ? 0 : NTKF.globalConfig.destid ? 1 : -1,
						userlevel: NTKF.globalConfig.userlevel,
						ref: NTKF.url,
						title: t.MOBILE ? NTKF.title.substr(0, 20) : NTKF.title
					}, !0), NTKF.siteid, a);
					clearTimeout(NTKF.inviteTimeID);
					try {
						this.ChatWindow.focus()
					} catch (e) {}
				}
				this.openedInWind ? NTKF.debug.Log("openedInWind: " +
					NTKF.icon.openedInWind + "", 1) : (c = c || NTKF.defaultConfig.settingid, this.getDestUid(c, a, function(a) {
					NTKF.globalConfig.destid = a;
					j()
				}))
			},
			createConnect: function() {
				if (!NTKF.IMPRESENCE) {
					NTKF.CACHE.get("fid") || NTKF.CACHE.save("fid", NTKF.createTimeID(2));
					var a, b, c = {
						siteid: NTKF.siteid,
						settingid: NTKF.settingid,
						surl: NTKF.url_service,
						r: NTKF.url_resource,
						ref: NTKF.url_referrer,
						fsid: NTKF.CACHE.get("fid"),
						cid: NTKF.globalConfig.machineid
					};
					NTKF.user.id && (c = d.extend({}, c, {
						u: NTKF.user.id,
						n: NTKF.user.name,
						s: NTKF.user.sessionid
					}));
					NTKF.debug.Log("NTKF.t2d.createConnect(); CONNECT_TYPE: " + NTKF.globalConfig.CONNECT_TYPE +
						", CONNECT_LEVEL:" + NTKF.globalConfig.CONNECT_LEVEL);
					if (1 == NTKF.globalConfig.CONNECT_LEVEL) NTKF.globalConfig.machineid || (NTKF.globalConfig.machineid =
						"guest" + [NTKF.randomChar(8), NTKF.randomChar(4), NTKF.randomChar(4), NTKF.randomChar(4), NTKF.randomChar(
							12)].join("-")), NTKF.user.id || (NTKF.user.id = NTKF.siteid + "_ISME9754_" + NTKF.globalConfig.machineid.substr(
						0, 21)), NTKF.globalConfig.islogin && NTKF.t2d.callTrail(NTKF.user.id,
						NTKF.globalConfig.machineid, 1), NTKF.globalConfig.CONNECT_TYPE = "NULL";
					else if ("COMETD" == NTKF.globalConfig.CONNECT_TYPE) NTKF.require(function() {
						NTKF.IMPRESENCE = new NTKF.impresence(c)
					}, "cometd.js?version=" + NTKF.version_cometd, NTKF.cometd);
					else {
						2 == NTKF.globalConfig.CONNECT_LEVEL && 1 !== NTKF.CACHE.get("opd") ? (NTKF.globalConfig.CONNECT_TYPE =
							"FLASH_BASE", a = NTKF.url_resource + "/fs/NTID.swf?version=" + NTKF.version_ntid) : (NTKF.globalConfig.CONNECT_TYPE =
							"FLASH_COMPLETE", a = NTKF.url_resource + "/fs/impresence.swf?version=" +
							NTKF.version_presence);
						b = ['<div style="position: absolute; z-index: 9996; top: -200px;" id="ntkf_flash_div_presence">', t.IE ? [
							'<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="https://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,28"  id="ntkf_flash_impresence" width="1" height="1">',
							'<param name="movie" value="' + a + '" />', '<param name="allowscriptaccess" value="always" />',
							'<param name="flashvars" value="' + d.toURI(c) + '" />', "</object>"
						].join("") : ['<embed src="' +
							a + '" id="ntkf_flash_impresence" width="1" height="1" flashvars="' + d.toURI(c) +
							'" allowscriptaccess="always" pluginspage="https://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" >',
							"</embed>"
						].join(""), "</div>"].join("");
						j("#ntkf_flash_div_presence").remove();
						var g = 3,
							i;
						(function() {
							0 >= g || (g--, document.body ? (s.insert(document.body, b), NTKF.IMPRESENCE = j("#ntkf_flash_impresence").first(),
								t.OLDIE && j(NTKF.IMPRESENCE).show()) : (i = arguments, setTimeout(function() {
								i.callee()
							}, 100)))
						})()
					}
				}
			}
		});
		NTKF.extend({
			IMConnectStatus: -1,
			fIM_isLastPage: function(a, b) {
				NTKF.debug.Log("NTKF.fIM_isLastPage(" + a + ', "' + b + '")')
			},
			fIM_presenceFlashReady: function(a, b) {
				NTKF.debug.Log('NTKF.fIM_presenceFlashReady("' + a + '", "' + b + '")');
				setTimeout(function() {
					NTKF.user.id = a || NTKF.user.id;
					NTKF.globalConfig.machineid = b || NTKF.globalConfig.machineid;
					NTKF.globalConfig.islogin && NTKF.t2d.callTrail(NTKF.user.id, NTKF.globalConfig.machineid, 1);
					!NTKF.E.fireEvent(document, "focus") && NTKF.E.fireEvent(document, "click")
				}, 1);
				return !0
			},
			fIM_onPresenceReceiveSysMessage: function(b, f) {
				NTKF.debug.Log('NTKF.fIM_onPresenceReceiveSysMessage("' + b + '", "' + NTKF.J.toJSONString(f) + '")', 1);
				var h, g, i = f.substr(0, f.indexOf("ntalker://")),
					k = f.substr(f.indexOf("ntalker://") + 10),
					i = i.replace(/\<.*?\>/ig, "");
				try {
					h = B.parseJSON(k)
				} catch (j) {
					a.Log(j);
					return
				}
				NTKF.globalConfig = d.extend(NTKF.globalConfig, {
					destid: h.id,
					destsid: h.sid
				});
				10 == h.calltype ? (g = NTKF.chatManage ? NTKF.chatManage.get(h.settingid, h.id) : null, NTKF.require(function() {
						clearTimeout(NTKF.inviteTimeID);
						g ? (NTKF.debug.Log("recoonnect"), g.reconnect(null, !1)) : 1 != NTKF.defaultConfig.autoopen ? (NTKF.debug.Log(
							"show tips"), NTKF.invite.showTips(h.id, i, h.sid, h.inviteid, h.settingid), NTKF.promptwindow.startPrompt(
							"", p.news_new, !0)) : h.settingid ? NTKF.globalConfig.newwindow || NTKF.B.MOBILE ? NTKF.t2d.openChatWindow(
							h.id, "", h.settingid) : NTKF.chatManage.open(h.settingid, null, h.id, {
							single: -1
						}) : NTKF.debug.Log("settingid is null", 3)
					}, "ntchat.js?version=" + NTKF.version_ntchat, NTKF.chatManage)) : /^(1|2)$/ig.test(h.calltype) ?
					NTKF.executeMarketing(h.content, !1, !1, "FLASH") : 0 == parseInt(h.calltype) && (/ntkf_core/i.test(NTKF.url_name) ?
						c(function() {
							clearTimeout(NTKF.inviteTimeID);
							NTKF.oldInvite.showWindow(h.id, i, h.sid, h.inviteid);
							NTKF.promptwindow.startPrompt("", p.news_new, !0)
						}, "chatEntrance.js?version=" + NTKF.version_entrance, NTKF.oldInvite) : NTKF.require(function() {
								clearTimeout(NTKF.inviteTimeID);
								NTKF.invite.showTips(h.id, i, h.sid, h.inviteid);
								NTKF.promptwindow.startPrompt("", p.news_new, !0)
							}, "ntchat.js?version=" + NTKF.version_ntchat,
							NTKF.chatManage));
				return !0
			},
			fIM_onGetFlashServer: function() {
				return !0
			},
			fIM_updateUserStatus: function(a, b) {
				NTKF.IMConnectStatus < a && NTKF.debug.Log("NTKF.fIM_updateUserStatus(" + a + ', "' + b + '")');
				NTKF.IMConnectStatus = a;
				if (2 == NTKF.IMConnectStatus) {
					NTKF.autoOpenInviteWindow();
					try {
						NTKF.IMPRESENCE.setPageFocus(!0, NTKF.title, NTKF.url, 1)
					} catch (c) {}
				}
				return !0
			},
			fIM_presenceSetIMSid: function(a) {
				NTKF.debug.Log("userToken:" + a, 1);
				NTKF.globalConfig.userToken = a;
				return !0
			},
			fIM_presenceSetMyClientID: function(a) {
				NTKF.url_presenceFlashGoUrl =
					a;
				return !0
			},
			fIM_setTChatGoServer: function(a) {
				NTKF.url_tChatFlashGoUrl = a;
				return !0
			}
		});
		NTKF.extend({
			fCACHE: {
				reNumber: 0,
				sessionData: null,
				checkFlash: function(a) {
					!NTKF.IMPRESENCE || !NTKF.IMPRESENCE.setJSData ? 3 < this.reNumber || (this.reNumber++, setTimeout(a, 500)) :
						a.call(this)
				},
				add: function(a, b, c) {
					!1 !== c && NTKF.debug.Log("NTKF.fCACHE.add(k:" + a + ", v:[" + typeof b + "])");
					this.checkFlash(function() {
						try {
							return NTKF.IMPRESENCE.setJSData(NTKF.trailID, a, A._replace(b, !0)), !0
						} catch (c) {
							return !1
						}
					})
				},
				remove: function(a) {
					this.add(a,
						null, !1)
				},
				get: function(a) {
					var b, c = {};
					if (!NTKF.IMPRESENCE || !NTKF.IMPRESENCE.getJSData) return c;
					try {
						b = NTKF.IMPRESENCE.getJSData(NTKF.trailID, a || ""), c = NTKF.J.parseJSON(b || "{}")
					} catch (d) {}
					return A._replace(c, !1)
				}
			}
		});
		NTKF.extend({
			waitManage: new NTKF.H,
			completeManage: new NTKF.H,
			visibilityManage: new NTKF.H,
			listenMouve: !1,
			outWindow: !1,
			curOpenWindow: 0,
			lastTimeClientY: 0,
			execMarketingNum: [],
			waitRequestTime: null,
			httpRequestTime: null,
			isCurrentPage: null,
			executeMarketing: function(a, b, c, g) {
				this.params = d.extend({
					trigger: 0,
					action: 0,
					httpintevel: 1,
					taskid: "",
					delaytime: "",
					promoid: "",
					promotype: 0,
					sizex: 320,
					sizey: 180,
					location: "center-center",
					effect: "fadein",
					invokeurl: "",
					param: null,
					skin: "marketing/1",
					templatetmode: 0,
					templatetcontent: "",
					jumpurl: "",
					kfconfigcode: "",
					invokesceneids: "",
					beforeLeaveShowMask: 0
				}, a);
				!0 !== b && (!0 !== c && 1 == this.params.action) && NTKF.statisticReceiveMarketing(9, null, g);
				NTKF.globalConfig.trailID ? (b = NTKF.CACHE.get("taskid." + this.params.promoid, !0) || 0, g = this.params.taskid
					.replace(NTKF.user.id + "_" + this.params.promoid +
						"_", ""), g <= b && !0 != c ? NTKF.debug.Log("curTaskID:" + g + " <= cacheTaskID:" + b + "(" + this.params.taskid +
						") && action:" + this.params.action + " != 0", 0) : (NTKF.debug.Log("saveTaskID: taskid." + this.params.promoid +
							" = " + g), NTKF.CACHE.save("taskid." + this.params.promoid, g), 0 == this.params.trigger ? this.completeManage
						.contains(this.params.promoid) || (b = NTKF.A.extend(this.params, {
							example: new NTKF.marketing(this.params)
						}), this.completeManage.add(this.params.promoid, b)) : 1 == this.params.trigger && (0 == this.params.action &&
							NTKF.waitManage.items(this.params.promoid) ?
							(NTKF.waitManage.remove(this.params.promoid), NTKF.fCACHE.remove("cache_" + this.params.promoid), 0 ===
								NTKF.waitManage.count() && NTKF.listenMouseOut(!0), NTKF.debug.Log("Cancel executive marketing. promoid:" +
									this.params.promoid + ", waitManage:" + NTKF.waitManage.count(), 1)) : NTKF.waitManage.contains(this.params
								.promoid) || (!1 !== d.inArray([""], NTKF.siteid) && (this.params.displaymask = 1), NTKF.waitManage.add(
									this.params.promoid, this.params), NTKF.B.MOBILE ? NTKF.immediate = !0 : NTKF.listenMouseOut(), NTKF.fCACHE
								.add("cache_" +
									this.params.promoid, this.params))))) : (NTKF.isDefined(NTKF.execMarketingNum[a.promoid]) || (NTKF.execMarketingNum[
					a.promoid] = 3), setTimeout(function() {
					NTKF.execMarketingNum[a.promoid]--;
					0 >= NTKF.execMarketingNum[a.promoid] ? NTKF.debug.Log(
						"Stop trying to perform function NTKF.executeMarketing", 2) : NTKF.executeMarketing(a, !0, c)
				}, 1E3))
			},
			statisticReceiveMarketing: function(a, b, c) {
				b = b || this.params;
				a = NTKF.url_manage_server + "rtmarket.php?" + d.toURI({
					m: "Index",
					a: "Coupon",
					s: NTKF.siteid,
					gid: NTKF.user.id,
					pid: NTKF.globalConfig.machineid,
					c: b.promoid,
					atype: a || 9,
					tid: b.taskid,
					token: b.param1,
					key: b.param || "",
					isid: b.invokesceneids,
					cw: b.cw || "0",
					request: c || "FLASH"
				});
				NTKF.C.load(a + "#rnd")
			},
			changePageCount: function(a) {
				NTKF.curOpenWindow = a;
				if (!0 == NTKF.immediate && 1 == NTKF.curOpenWindow) {
					var b = this;
					b.waitManage.each(function(a, c) {
						NTKF.debug.Log("execute cache marketing", 1);
						b.waitManage.remove(a);
						NTKF.fCACHE.remove("cache_" + a);
						var e = d.extend(c, {
								trigger: 0
							}),
							e = d.extend(e, {
								example: new b.marketing(NTKF.A.extend(e, {
									cw: NTKF.curOpenWindow
								}))
							});
						b.completeManage.add(a,
							e);
						0 === NTKF.waitManage.count() && NTKF.listenMouseOut(!0)
					})
				}
			},
			loadCacheMarketing: function() {
				var a = NTKF.fCACHE.get(),
					b;
				for (b in a) "function" != typeof a[b] && a[b] && -1 < b.indexOf("cache_") && NTKF.executeMarketing(a[b], !1, !
					0);
				this.loadedfCACHE = !0
			},
			listenMouseOut: function(a) {
				var b = this,
					c = function(a) {
						a = NTKF.E.fixEvent(a);
						(a.relatedTarget || a.toElement) && !b.outWindow && b.setMouseOutWindow(a, !1)
					},
					d = function(a) {
						a = NTKF.E.fixEvent(a);
						!a.relatedTarget && !a.toElement && b.setMouseOutWindow(a, !0)
					},
					i = function(a) {
						a = NTKF.E.fixEvent(a);
						b.setMouseOutWindow(a, "mousemove")
					};
				!0 === a ? (NTKF.E.removeEvent(document, "mouseover", c), NTKF.E.removeEvent(document, "mouseout", d), NTKF.E.removeEvent(
					document, "mousemove", i)) : b.listenMouve || (NTKF.E.addEvent(document, "mouseover", c), NTKF.E.addEvent(
					document, "mouseout", d), NTKF.E.addEvent(document, "mousemove", i))
			},
			setMouseOutWindow: function(a, b) {
				if ("mousemove" === b) this.lastTimeClientY = 0 < a.clientY ? a.clientY : this.lastTimeClientY;
				else {
					this.outWindow = b;
					var c = this;
					0 > a.clientY && 50 > c.lastTimeClientY && (NTKF.debug.Log("mouseoutWindow curOpenWindow:" +
						NTKF.curOpenWindow + ", waitManage:(" + c.waitManage.count() + "), completeManage:(" + NTKF.completeManage.count() +
						")", 5), 1 == NTKF.curOpenWindow && c.waitManage.each(function(a, b) {
						NTKF.debug.Log("execute cache marketing", 1);
						c.waitManage.remove(a);
						NTKF.fCACHE.remove("cache_" + a);
						var e = NTKF.A.extend(b, {
								trigger: 0
							}),
							e = NTKF.A.extend(e, {
								example: new c.marketing(NTKF.A.extend(e, {
									cw: NTKF.curOpenWindow
								}))
							});
						c.completeManage.add(a, e);
						0 === NTKF.waitManage.count() && NTKF.listenMouseOut(!0)
					}))
				}
			}
		});
		NTKF.marketing = NTKF.Class.create();
		NTKF.marketing.prototype = {
			id: "ntkf_marketing_background",
			className: "ntkf-marketing-bg",
			position: "fixed",
			element: null,
			param: null,
			width: 0,
			height: 0,
			location: {},
			layout: "float",
			margin: [0, 7, 7, 57, 7],
			initialize: function(a) {
				NTKF.debug.Log("NTKF.marketing.initialize(promoid:" + a.promoid + ")");
				this.param = a;
				this.config = d.extend({}, NTKF.defaultConfig);
				var b, a = NTKF.bodyOffset;
				if ("" == this.param.promoid || "" == this.param.taskid) NTKF.debug.Log(
					"Active marketing is not configured. promoid:" + this.param.promoid + ", taskid:" +
					this.param.taskid, 3);
				else {
					if (0 == this.param.promotype) {
						if (1 == NTKF.CACHE.get("m.pt0", !0)) {
							NTKF.debug.Log("Every time login show only a coupons");
							return
						}
						NTKF.CACHE.save("m.pt0", 1)
					}
					if (!NTKF.visibilityManage.contains(this.param.promotype)) {
						NTKF.visibilityManage.add(this.param.promotype, this.param);
						"0" == this.param.promotype && NTKF.visibilityManage.contains("1") && (b = NTKF.visibilityManage.items("1").promoid,
							(b = NTKF.completeManage.items(b).example) && b.close());
						this.param.location = this.param.location.toLowerCase();
						/top-banner|bottom-banner/ig.test(this.param.location) && (this.layout = "banner");
						this.id += "_type" + this.param.promotype + "_" + this.param.promoid;
						this.className += "-" + this.param.promotype + ("float" == this.layout ? 1 : 2);
						if (0 == this.param.promotype) this.margin = "float" == this.layout ? [0, 7, 7, 57, 7] : [0, 0, 213, 0, 0];
						else if (this.margin = "float" == this.layout ? [0, 0, 0, 0, 0] : [0, 0, 213, 0, 0], NTKF.chatManage && NTKF.chatManage
							.inited) return;
						1 == this.param.promotype && "" == this.param.kfconfigcode && (this.margin[3] = 0);
						this.width = (this.param.sizex ||
							280) + this.margin[2] + this.margin[4];
						this.height = (this.param.sizey || 80) + this.margin[1] + this.margin[3];
						switch (this.param.location) {
							case "left-top":
								this.location = {
									v: "left",
									h: "top",
									left: 0,
									top: 0
								};
								break;
							case "center-top":
								this.location = {
									v: "left",
									h: "top",
									left: (a.width - this.width) / 2,
									top: 0
								};
								break;
							case "right-top":
								this.location = {
									v: "right",
									h: "top",
									right: 0,
									top: 0
								};
								break;
							case "left-center":
								this.location = {
									v: "left",
									h: "top",
									left: 0,
									top: (a.viewH - this.height) / 2
								};
								break;
							case "right-center":
								this.location = {
									v: "right",
									h: "top",
									right: 0,
									top: (a.viewH - this.height) / 2
								};
								break;
							case "left-bottom":
								this.location = {
									v: "left",
									h: "bottom",
									left: 0,
									bottom: 0
								};
								break;
							case "center-bottom":
								this.location = {
									v: "left",
									h: "bottom",
									left: (a.width - this.width) / 2,
									bottom: 0
								};
								break;
							case "right-bottom":
								this.location = {
									v: "right",
									h: "bottom",
									right: 0,
									bottom: 0
								};
								break;
							case "top-banner":
								"banner" == this.layout && (this.width = Math.min(a.width, a.viewW));
								this.location = {
									v: "left",
									h: "top",
									left: 0,
									top: 0
								};
								this.param.effect = "heighten";
								break;
							case "bottom-banner":
								"banner" == this.layout &&
									(this.width = Math.min(a.width, a.viewW));
								this.location = {
									v: "left",
									h: "bottom",
									left: 0,
									bottom: 0
								};
								this.param.effect = "flyin";
								break;
							default:
								this.location = {
									v: "left",
									h: "top",
									left: (a.width - this.width) / 2,
									top: (a.viewH - this.height) / 2
								}
						}
						b = Math.min(a.width, a.viewW);
						NTKF.isDefined(this.location.right) || (this.location.right = b - this.location.left - this.width);
						NTKF.isDefined(this.location.bottom) || (this.location.bottom = a.viewH - this.location.top - this.height);
						NTKF.isDefined(this.location.left) || (this.location.left = b - this.location.right -
							this.width - 2);
						NTKF.isDefined(this.location.top) || (this.location.top = a.viewH - this.location.bottom - this.height - 2);
						NTKF.B.IE6 || !NTKF.B.CSS1Compat && NTKF.B.IE ? (this.position = "absolute", this.location.v = "left", this.location
								.h = "top", -1 < this.param.location.indexOf("right") && (this.location.left -= 2)) : this.position =
							"fixed";
						"float" != this.layout && "top-banner" == this.param.location && (this.position = "mystatic");
						this.create()
					}
				}
			},
			statistic: function(a) {
				NTKF.debug.Log("NTKF.marketing.statistic(atype:" + a + ")");
				a = NTKF.url_manage_server +
					"rtmarket.php?" + d.toURI({
						m: "Index",
						a: "Coupon",
						s: NTKF.siteid,
						gid: NTKF.user.id,
						pid: NTKF.globalConfig.machineid,
						c: this.param.promoid,
						atype: a,
						tid: this.param.taskid,
						token: this.param.param1,
						key: this.param.param ? this.param.param : "",
						isid: this.param.invokesceneids,
						cw: this.param.cw || ""
					});
				NTKF.C.load(a + "#rnd")
			},
			create: function() {
				NTKF.debug.Log("NTKF.marketing.create()");
				var a = NTKF.bodyOffset;
				j("#" + this.id).remove();
				NTKF.C.load(NTKF.imagesResources.url_image + "/" + (this.param.skin || "marketing/1") + "/default.css?version=" +
					NTKF.version);
				var b = this,
					c, d, i, k, l, m, n, s, q, t;
				"float" == this.layout ? (c = this.width, d = this.height, i = this.width - this.margin[2] - this.margin[4], k =
					this.height - this.margin[1] - this.margin[3], l = 0, m = this.height - (1 == this.param.promotype ? 35 : this
						.margin[3]), n = this.width, s = this.margin[3], q = "display:block;visibility:hidden;width:" + this.width +
					"px;height:" + this.height + "px;position:" + this.position + ";left:" + this.location.left + "px;" + this.location
					.h + ":" + this.location[this.location.h] + "px;z-index:299999;") : (c = this.margin[2] +
					this.param.sizex + this.margin[4], d = this.margin[1] + this.height + this.margin[3], i = this.param.sizex, k =
					this.height - this.margin[1] - this.margin[3], l = i + this.margin[4], m = 0, n = this.margin[2], s = this.height,
					q = "display:block;visibility:hidden;width:100%;height:" + this.height + "px;", q += "none" == this.position ?
					"" : "position:" + this.position + ";left:" + this.location.left + "px;/*" + this.location.v + ":" + this.location[
						this.location.v] + "px;*/" + this.location.h + ":" + this.location[this.location.h] + "px;z-index:299999;");
				t = "left:" + this.margin[4] +
					"px;top:" + this.margin[1] + "px;width:" + i + "px;height:" + k + "px;";
				c = '<div class="ntkf-marketing-container" style="' + ("display:block;width:" + c + "px;height:" + d + "px;") +
					'"><div class="ntkf-marketing-close"></div><div class="ntkf-marketing-status"></div>';
				switch (this.param.templatetmode) {
					case 1:
						c += '<iframe class="ntkf-marketing-content" style="' + t + 'overflow-x:hidden;overflow-y:auto;" src="' + this
							.param.templatetcontent + '" frameborder="0" width="' + i + '" height="' + k +
							'" marginheight="0" marginwidth="0" scrolling="auto"></iframe>';
						break;
					default:
						c += '<div class="ntkf-marketing-content" style="' + t + '">', c = 2 == this.param.templatetmode ? c + this.param
							.templatetcontent.replace(/&LT;/ig, "<").replace(/&GT;/ig, ">").replace(/&QUOT;/ig, '"').replace(/&#039/ig,
								"'") : "" != this.param.jumpurl ? c + ('<a href="' + this.param.jumpurl + '" target="_blank"><img src="' +
								this.param.templatetcontent + '" border="0" /></a>') : c + ('<img src="' + this.param.templatetcontent +
								'" border="0" />'), c += "</div>"
				}
				c += '<div class="ntkf-marketing-bottom" style="overflow:visible;left:' +
					l + "px;top:" + m + "px;width:" + n + "px;height:" + s + 'px;">' + (!this.param.kfconfigcode && 1 == this.param
						.promotype ? "" :
						'<table border="0" cellpadding="0" cellspacing="0" style="height:100%;width:100%;border:0px;"><tr><td ' + (
							"banner" == this.layout ? 'valign="bottom" align="left" style="vertical-align:bottom"' : 'align="center"') +
						'><div class="ntkf-marketing-download" style="display:' + (NTKF.B.IE6 ? "inline" : "inline-block") +
						';"></div><div class="ntkf-marketing-open" style="display:' + (this.param.kfconfigcode ? NTKF.B.IE6 ||
							NTKF.B.IE7 ? "inline" : "inline-block" : "none") + ';"></div></td></tr></table>') + "</div></div>";
				this.element = j({
					id: this.id,
					className: this.className,
					style: q
				}).html(c);
				if (!this.param.kfconfigcode && 1 == this.param.promotype || 1 == this.param.displaymask || 1 == this.config.displaymask)
					1 == this.param.templatetmode && j("div.ntkf-marketing-close").css("right", "20px"), j({
						className: this.id + "-opacitybg",
						style: "position:absolute;left:0;top:0;width:100%;height:" + a.height +
							'px;background:#000;border:none;margin:0;padding:0;filter:alpha(opacity=60);-moz-opacity:0.6;-webkit-opacity:0.6;-khtml-opacity:0.6;opacity:0.6;-ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=60)";z-index:199999;display:block;visibility:hidden;'
					}),
					j({
						tagName: "iframe",
						className: this.id + "-iframe",
						style: "position:absolute;left:0;top:0;width:100%;height:" + a.height +
							'px;border:none;margin:0;padding:0;filter:alpha(opacity=60);-moz-opacity:0.6;-webkit-opacity:0.6;-khtml-opacity:0.6;opacity:0.6;-ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=60)";z-index:199999;display:block;'
					});
				j("#" + b.id + " div.ntkf-marketing-opacitybg").bind("contextmenu", function() {
					return !1
				}).bind("selectstart", function() {
					return !1
				});
				j("#" + b.id + " div.ntkf-marketing-close").hover(function(a,
					b) {
					j(b).css("opacity", 0.8)
				}, function(a, b) {
					j(b).css("opacity", 1)
				}).bind("click", function() {
					b.close.call(b)
				});
				j("#" + b.id + " div.ntkf-marketing-open").bind("click", function() {
					0 != b.param.promotype && b.close.call(b);
					b.statistic(2);
					NTKF.globalConfig.chattype = 1 == b.param.promotype ? 8 : 9;
					NTKF.globalConfig.chatvalue = b.param.promoid;
					NTKF.im_openInPageChat(b.param.kfconfigcode, "", "", {
						single: -1
					})
				});
				j("#" + b.id + " div.ntkf-marketing-download").bind("click", function(a) {
					a = NTKF.E.fixEvent(a).target;
					NTKF.D.indexOfClass(a,
						"ntkf-marketing-download-disabled") || b.showtip()
				});
				b.show();
				0 == b.param.promotype && NTKF.promptwindow.startPrompt("", p.flash_title, !0);
				"absolute" == b.position && (b.lock.call(b), setInterval(function() {
					b.lock.call(b)
				}, 20));
				return !0
			},
			show: function() {
				NTKF.debug.Log("NTKF.marketing.show()");
				var a, b, c = j("#" + this.id),
					d = j("#" + this.id + ", #" + this.id + " div.ntkf-marketing-container");
				b = NTKF.A.get(this.location, [this.location.h]);
				a = "top" == this.location.h ? NTKF.bodyOffset.viewY + NTKF.bodyOffset.viewH : 0 - this.height;
				switch (this.param.effect) {
					case "fadein":
						c.css("visibility",
							"visible").css("opacity", 0.01);
						NTKF.D.animate(d, {
							opacity: 100
						}, 600, "swing");
						break;
					case "flyin":
						NTKF.debug.Log("::::" + this.location.h);
						c.css("visibility", "visible").css(this.location.h, a + "px");
						NTKF.D.animate(c, b, 600, "easeInOutCubic");
						break;
					case "heighten":
						v.scrollTop = 0;
						document.body.scrollTop = 0;
						d.css("overflow", "hidden").css("visibility", "visible").css("height", "1px");
						NTKF.D.animate(d, {
							height: this.height
						}, 600, "easeInOutCubic");
						break;
					default:
						d.css("visibility", "visible")
				}
				j("." + this.id + "-opacitybg").css("visibility",
					"visible").anim({
					opacity: 60
				}, 600, "easeInOutCubic");
				this.statistic(0);
				"undefined" != typeof ntcall_onShowInvitation && ntcall_onShowInvitation(this.param.promotype, this.param.promoid)
			},
			close: function() {
				NTKF.debug.Log("NTKF.marketing.close()");
				var a = {},
					b;
				j("#" + this.id + " div.ntkf-marketing-content").attr("src", "about:blank");
				switch (this.param.effect) {
					case "fadein":
						j("#" + this.id).hide(!0, !0);
						break;
					case "flyin":
						b = "top" == this.location.h ? NTKF.bodyOffset.viewY + NTKF.bodyOffset.viewH : 0 - this.height;
						a[this.location.h] =
							b;
						NTKF.D.animate(j("#" + this.id), a, 600, "easeInOutCubic", function(a) {
							j(a).remove();
							j("." + this.id + "-opacitybg, ." + this.id + "-iframe").remove()
						});
						break;
					case "heighten":
						NTKF.D.animate(j("#" + this.id + ", #" + this.id + " .ntkf-marketing-container"), {
							height: 1
						}, 600, "easeInOutCubic", function(a) {
							j(a).remove();
							j("." + this.id + "-opacitybg, ." + this.id + "-iframe").remove()
						});
						break;
					default:
						j("#" + this.id).remove()
				}
				j("." + this.id + "-opacitybg, ." + this.id + "-iframe").hide(!0, !0);
				NTKF.promptwindow.stopPrompt();
				NTKF.visibilityManage.remove(this.param.promotype)
			},
			showtip: function() {
				var a = this,
					b = j(".ntkf-marketing-tip-container"),
					c = document.body,
					d = j(document.body).rect();
				b.first() ? j(".ntkf-marketing-tip-container, .ntkf-marketing-opacitybg, .ntkf-marketing-iframe").show() : (b =
					j({
						className: "ntkf-marketing-tip-container",
						style: "visibility:hidden;display:none;left:" + (d.viewW - 381) / 2 + "px;top:" + ((d.viewH - 206) / 2 - d.top) +
							"px;"
					}, c), j({
						className: "ntkf-marketing-opacitybg",
						style: "height:" + d.height + "px;visibility:hidden;"
					}, c), j({
						tagName: "iframe",
						className: "ntkf-marketing-iframe",
						style: "height:" + d.height + "px;"
					}, c));
				b.html(['<div class="ntkf-marketing-tip-title">' + p.coupon_title + "</div>",
					'<div class="ntkf-marketing-tip-close"></div><div class="ntkf-marketing-tip-content"><ul>',
					'<li><dl><dt><span class="ntkf-marketing-red">*</span>' + p.coupon_mobile +
					'</dt><dd><input type="text" name="ntkf-marketing-phone" onkeyup="this.value=this.value.replace(/\\D/g,\'\')" onafterpaste="this.value=this.value.replace(/\\D/g,\'\')" /></dd><dd class="ntkf-marketing-news"><span class="ntkf-marketing-phone-news">' +
					p.coupon_mobile_default_text + "</span></dd></dl></li>",
					'<li><dl><dt><span class="ntkf-marketing-red">*</span>' + p.coupon_name +
					'</dt><dd><input type="text" name="ntkf-marketing-nickname" /></dd><dd class="ntkf-marketing-news"><span class="ntkf-marketing-nickname-news">' +
					p.coupon_name_default_text + "</span></dd></dl></li>",
					'<li class="ntkf-center"><input type="button" name="ntkf_marketing_button_send" class="ntkf-marketing-button" value="' +
					p.coupon_button_receive + '" /></li>', "</ul></div>"
				].join(""));
				j(".ntkf-marketing-opacitybg").css("opacity",
					0.01).css("visibility", "visible").anim({
					opacity: 60
				});
				j(b).css("opacity", 0.01).css("visibility", "visible").show(!0);
				j("div.ntkf-marketing-tip-close").bind("click", function() {
					a.closetip()
				}).hover(function(a, b) {
					j(b).css("opacity", 0.8)
				}, function(a, b) {
					j(b).css("opacity", 1)
				});
				j("input[name=ntkf_marketing_button_send]").bind("click", function() {
					a.sendCoupon()
				});
				j("input[name=ntkf-marketing-phone]").bind("blur", function() {
					var a = j("input[name=ntkf-marketing-phone]").value(),
						b = j("span.ntkf-marketing-phone-news");
					"" == a || !/^1[3|4|5|8][0-9]\d{8}$/.test(a) ? b.replaceClass("ntkf-marketing-new-right",
						"ntkf-marketing-new-error").html(p.coupon_mobile_error_text) : b.replaceClass("ntkf-marketing-new-error",
						"ntkf-marketing-new-right").html("&nbsp;")
				});
				j("input[name=ntkf-marketing-nickname]").bind("blur", function() {
					var a = j("input[name=ntkf-marketing-nickname]").value(),
						b = j("span.ntkf-marketing-nickname-news");
					"" == a || 2 > a.length ? b.replaceClass("ntkf-marketing-new-right", "ntkf-marketing-new-error").html(p.coupon_name_error_text) :
						b.replaceClass("ntkf-marketing-new-error", "ntkf-marketing-new-right").html("&nbsp;")
				})
			},
			closetip: function() {
				return j(".ntkf-marketing-tip-container, .ntkf-marketing-opacitybg, .ntkf-marketing-iframe").hide(!0, !0)
			},
			sendCoupon: function() {
				NTKF.debug.Log("NTKF.marketing.sendCoupon()");
				var a = this,
					b;
				b = j("span.ntkf-marketing-phone-news");
				var c = j("span.ntkf-marketing-nickname-news"),
					g = j("input[name=ntkf-marketing-phone]").value(),
					i = j("input[name=ntkf-marketing-nickname]").value();
				"" == g || !/^1[3|4|5|8][0-9]\d{8}$/.test(g) ?
					b.replaceClass("ntkf-marketing-new-right", "ntkf-marketing-new-error").html(p.coupon_mobile_error_text) : (b.replaceClass(
						"ntkf-marketing-new-error", "ntkf-marketing-new-right").html("&nbsp;"), "" == i || 2 > i.length ? c.replaceClass(
						"ntkf-marketing-new-right", "ntkf-marketing-new-error").html(p.coupon_name_error_text) : (c.replaceClass(
							"ntkf-marketing-new-error", "ntkf-marketing-new-right").html("&nbsp;"), b = NTKF.url_manage_server +
						"rtmarket.php?" + d.toURI({
							m: "Index",
							a: "Coupon",
							s: NTKF.siteid,
							gid: NTKF.user.id,
							pid: NTKF.globalConfig.machineid,
							atype: 1,
							c: a.param.promoid,
							phone: g,
							nickname: i,
							tid: a.param.taskid,
							token: a.param.param1,
							key: a.param.param,
							isid: a.param.invokesceneids
						}), NTKF.C.load(b, function() {
							"complete" == exp.status ? "undefined" == typeof ntkf_active_marketing_send_number ? (a.showCouponStat({
								result: -1
							}), NTKF.debug.Log("send coupon failure", 3)) : a.showCouponStat(ntkf_active_marketing_send_number) : a.showCouponStat({
								result: -1
							})
						})))
			},
			showCouponStat: function(a) {
				NTKF.debug.Log("NTKF.marketing.showCouponStat()");
				var b = this,
					c = "<ul>",
					d;
				1 == a.result ?
					(c += '<li class="ntkf-marketing-complete"><dl>', d = p.coupon_button_complete, j("div.ntkf-marketing-download")
						.addClass("ntkf-marketing-download-disabled")) : (c += '<li class="ntkf-marketing-failure"><dl>', d = p.coupon_button_close);
				c += '<dd class="ntkf-marketing-result-desc">' + p.coupon_send_complete +
					'</dd></dl></li><li class="ntkf-center"><input type="button" name="ntkf_marketing_button_complete" class="ntkf-marketing-button" value="' +
					d + '" /></li></ul>';
				j("div.ntkf-marketing-tip-content").html(c);
				j("input[name=ntkf_marketing_button_complete]").bind("click",
					function() {
						b.closetip.call(b)
					});
				a.error && j("dd.ntkf-marketing-result-desc").html(a.error)
			},
			lock: function() {
				this.lockTop(j("#" + this.id), NTKF.bodyOffset.viewY + this.location.top, NTKF.bodyOffset)
			},
			lockTop: function(a, b) {
				if (a.first()) {
					var c = (b - parseInt(a.css("top"))) / 10,
						c = parseInt(a.css("top")) + c;
					a.css("top", c + "px")
				}
			}
		};
		NTKF.extend({
			pageData: {
				gData: {},
				get: function(a) {
					if (a) {
						var b = {},
							a = NTKF.isArray(a) ? a : [a];
						this.gData = NTKF.extend(this.gData, {
							list: [],
							other: []
						});
						for (var c = 0; c < a.length; c++) {
							var d = a[c],
								i = !1,
								j =
								d.url_preg.test(NTKF.url);
							j && d.list && (this.gData.list = NTKF.A.extend(this.gData.list, this.List(d)), i = !0);
							if (j && d.pattrs) {
								for (var l in d.pattrs) "function" != typeof d.pattrs[l] && (b[l] = this.attrValue(d.pattrs[l]));
								this.gData.other = b;
								i = !0
							}
							if (i) break
						}
						return this.gData
					}
					NTKF.debug.Log("no page data config", 2)
				},
				List: function(a) {
					for (var b = [], c = a.index || 0, d = j(a.list), i = c; i < d.length; i++) {
						b[i - c] = {};
						for (var k in a.attrs) "function" != typeof a.attrs[k] && (b[i - c][k] = this.attrValue(a.attrs[k], d.get(i)))
					}
					return b
				},
				attrValue: function(a,
					b) {
					var c = "";
					a.selector && (b = j(a.selector, b).get(a.index || 0));
					c = a.attr ? j(b).attr(a.attr) : (j(b).html() || "").toString().replace(/^\s*|\s*$/ig, "");
					a.preg && (c = c.replace(a.preg, a.preg_index || ""));
					return c
				}
			},
			scrollTime: [],
			sendViewComment: function() {
				if (!(1 != NTKF.pageLevel || !1 === NTKF.A.inArray(["kf_10300"], NTKF.siteid))) {
					var a = Math.max(j("#goods_div_2").rect("height") + j("div.consulting_list").rect("height"), 500),
						b = function(a) {
							NTKF.C.load(NTKF.url_trail + "/userinfo.php?" + NTKF.A.toURI({
								action: "save",
								siteid: NTKF.siteid,
								uid: NTKF.user.id,
								uname: NTKF.user.name,
								cid: NTKF.globalConfig.machineid,
								ttl: NTKF.title,
								ref: NTKF.url_referrer,
								orderid: NTKF.globalConfig.orderid,
								orderprice: NTKF.globalConfig.orderprice,
								sid: NTKF.globalConfig.trailID,
								log: 0,
								url: "5" == a ? "#5second=1" : "#goodcomment=1"
							}));
							NTKF.debug.Log("send trail good comment." + a, 1);
							c(a, !0)
						},
						c = function(a, b) {
							NTKF.scrollTime[a] && (clearTimeout(NTKF.scrollTime[a]), NTKF.scrollTime[a] = b ? !1 : null, NTKF.debug.Log(
								"Stop scrollTime[" + a + "]"))
						};
					NTKF.E.addEvent(l, "scroll", function() {
						var d =
							NTKF.bodyOffset,
							d = d.height - d.viewY - d.viewH;
						d < a ? (!NTKF.scrollTime["10"] && !1 !== NTKF.scrollTime["10"] && (NTKF.debug.Log(
							"Wait 10 seconds to send read reviews trajectory"), NTKF.scrollTime["10"] = setTimeout(function() {
							b("10")
						}, 1E4)), !NTKF.scrollTime["5"] && !1 !== NTKF.scrollTime["5"] && (NTKF.debug.Log(
							"Wait 5 seconds to send read reviews trajectory"), NTKF.scrollTime["5"] = setTimeout(function() {
							b("5")
						}, 5E3))) : d > a && (c("10"), c("5"))
					})
				}
			}
		});
		document.body ? NTKF.initGlobalVariable() : NTKF.ready(function() {
			NTKF.initGlobalVariable()
		});
		NTKF.Flash.version_example = function() {
			return [NTKF.IMPRESENCE.getPresenceVersion() || "", NTKF.version_presence]
		}
	}
})();
